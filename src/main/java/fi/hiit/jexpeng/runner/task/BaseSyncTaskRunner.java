package fi.hiit.jexpeng.runner.task;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public abstract class BaseSyncTaskRunner extends AbstractTaskRunner implements ITaskRunner {
    protected IRunContextEventListener mRunContextEventListener;

    @Override
    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener = new IRunContextEventListener() {
            public void trigger(Event event) {
                //System.out.println("Ktask: " + this);
                switch (event.getEventType()) {
                    case TASK_END:
                        mNumTasksExecuted++;

                        if (mNumTasksExecuted < mNumTasksToExecute) {
                            // Start the next task
                            mCurrentIndexPos = nextTaskIndexPos(mCurrentIndexPos, mNumTasksExecuted);
                            execute(experimentRunContext, event.getTaskGroup());
                        }
                        else {
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

        // Start the execution process
        execute(experimentRunContext, taskGroup);
    }
}
