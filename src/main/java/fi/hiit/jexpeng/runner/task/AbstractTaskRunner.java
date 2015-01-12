package fi.hiit.jexpeng.runner.task;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;


public abstract class AbstractTaskRunner implements ITaskRunner {
    protected int mCurrentIndexPos;
    protected int mNumTasksExecuted;
    protected int mNumTasksToExecute;
    protected int[] mTaskIndex;

    /** Initialize the runner */
    public abstract void init(final ExperimentRunContext experimentRunContext);

    /** Start the runner */
    public abstract void start(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup);

    public void execute(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        // Get the current task according to the index and start it
        getCurTask(taskGroup).start(experimentRunContext, taskGroup);
    }

    public Task getCurTask(TaskGroup taskGroup) {
        return taskGroup.get(mTaskIndex[mCurrentIndexPos]);
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
