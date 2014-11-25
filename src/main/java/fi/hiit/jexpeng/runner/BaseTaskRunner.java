package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public abstract class BaseTaskRunner implements ITaskRunner {
    protected int mCurrentIndexPos;
    protected int mNumTasksExecuted;
    protected int mNumTasksToExecute;
    protected int[] mTaskIndex;
    protected IRunContextEventListener mRunContextEventListener;

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

    public void execute(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        // Get the current task according to the index and start it
        taskGroup
            .get(mTaskIndex[mCurrentIndexPos])
            .start(experimentRunContext, taskGroup);
    }

    /** Initialize the index */
    protected int[] initTaskIndex(int numTasksToExecute) {
        int[] ret = new int[numTasksToExecute];

        // Initialize index to sequential order by default
        for (int i=0; i<numTasksToExecute; i++) {
            ret[i] = i;
        }

        return ret;
    }

    /** Set the next index index value */
    protected abstract int initTaskIndexPos();

    /** Set the next index index value */
    protected abstract int nextTaskIndexPos(int currentTaskIndexPos, int numTasksExecuted);
}
