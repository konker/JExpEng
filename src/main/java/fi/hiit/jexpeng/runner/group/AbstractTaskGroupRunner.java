package fi.hiit.jexpeng.runner.group;

import java.util.List;

import fi.hiit.jexpeng.Experiment;
import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.runner.task.ITaskRunner;


/**
 * BaseTaskGroupRunner
 *
 * @author Konrad Markus <konker@luxvelocitas.com>
 *
 */
public abstract class AbstractTaskGroupRunner implements ITaskGroupRunner {
    private final List<ITaskRunner> mTaskRunners;

    protected int mCurrentTaskGroupIndexPos;
    protected int mNumTaskGroupsExecuted;
    protected int mNumTaskGroupsToExecute;
    protected int[] mTaskGroupIndex;

    public AbstractTaskGroupRunner(List<ITaskRunner> taskRunners) {
        mTaskRunners = taskRunners;
    }

    /** Initialize the TaskGroup runner */
    public abstract void init(final ExperimentRunContext experimentRunContext);

    /** Start the TaskGroup runner */
    public abstract void start(final ExperimentRunContext experimentRunContext);

    public void execute(final ExperimentRunContext experimentRunContext) {
        // Get the current task group according to the index
        TaskGroup taskGroup = getCurTaskGroup(
                                experimentRunContext.getExperiment());

        // [FIXME: how to choose the task runner?]
        ITaskRunner taskRunner = mTaskRunners
                                    .get(mCurrentTaskGroupIndexPos);

        // Initialize the current task runner
        taskRunner.init(experimentRunContext);

        // Start the current task group
        taskGroup.start(experimentRunContext);

        // Apply the task runner to the current task group
        taskRunner.start(experimentRunContext, taskGroup);
    }

    public TaskGroup getCurTaskGroup(Experiment experiment) {
        return experiment.getTaskGroup(mTaskGroupIndex[mCurrentTaskGroupIndexPos]);
    }

    /** Initialize the index */
    protected int[] initTaskGroupIndex(int numTasksGroupsToExecute) {
        int[] ret = new int[numTasksGroupsToExecute];

        // Initialize index to sequential order by default
        for (int i=0; i<ret.length; i++) {
            ret[i] = i;
        }

        return ret;
    }

    /** Set the next index index value */
    protected abstract int initTaskGroupIndexPos();

    /** Set the next index index value */
    protected abstract int nextTaskGroupIndexPos(int currentTaskGroupIndexPos, int numTaskGroupsExecuted);
}
