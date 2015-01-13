package fi.hiit.jexpeng.runner.task;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public abstract class BaseConcurrentTaskRunner extends AbstractTaskRunner implements ITaskRunner {
    protected IRunContextEventListener mRunContextEventListener;
    protected Set<TaskThread> mThreadGroup;

    @Override
    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener = new IRunContextEventListener() {
            @Override
            public void trigger(Event event) {
                //System.out.println("Ktask: " + this);
                switch (event.getEventType()) {
                    case TASK_END:
                        mNumTasksExecuted++;

                        if (mNumTasksExecuted == mNumTasksToExecute) {
                            // End of the TaskGroup
                            experimentRunContext.removeRunContextEventListener(mRunContextEventListener);
                            event.getTaskGroup().complete(experimentRunContext);
                        }

                    default:
                        return;
                }
            }
        };

        experimentRunContext.addRunContextEventListener(mRunContextEventListener);
    }

    @Override
    public void start(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        mNumTasksToExecute = taskGroup.size();
        mNumTasksExecuted = 0;

        // Initialize the index, allow subclass to override this
        mTaskIndex = initTaskIndex(mNumTasksToExecute);

        // Fetch the starting position, must be implemented by the subclass
        mCurrentIndexPos = initTaskIndexPos();

        // Create a Set to hold TaskThread references
        mThreadGroup = new CopyOnWriteArraySet<TaskThread>();

        createThreads(experimentRunContext, taskGroup);

        startThreads();

        joinThreads();

        mThreadGroup.clear();
    }

    protected void createThreads(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        // Create a TaskThread for each task and add it to the group
        for (int i=0; i<mNumTasksToExecute; i++) {
            Task task = getCurTask(taskGroup);
            mThreadGroup.add(new TaskThread(experimentRunContext, taskGroup, task));

            mCurrentIndexPos = nextTaskIndexPos(mCurrentIndexPos, mNumTasksExecuted);
        }
    }

    protected void startThreads() {
        // Start all the TaskThreads
        for (TaskThread t : mThreadGroup) {
            t.start();
        }
    }

    protected void joinThreads() {
        // Join on all the TaskThreads
        for (TaskThread t : mThreadGroup) {
            try {
                //[FIXME: should we have a (user-configurable?) timeout here?]
                t.join();
            }
            catch (InterruptedException ex) {
                //[FIXME: what to do here?]
                ex.printStackTrace();
            }
        }
    }
}
