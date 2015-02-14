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
public abstract class AbstractStepTaskGroupRunner implements IStepTaskGroupRunner {
    public static final int START_INDEX = -1;

    protected enum TaskGroupStep {
        NOT_STARTED,
        GET_TASK_GROUP_INDEX,
        START_TASK_GROUP,
        START_TASK_RUNNER,
        ENDED
    }

    protected final List<ITaskRunner> mTaskRunners;

    protected int mCurrentTaskGroupIndexPos;
    protected int mNumTaskGroupsExecuted;
    protected int mNumTaskGroupsToExecute;
    protected int[] mTaskGroupIndex;
    protected boolean mAutoStep;
    protected TaskGroupStep mCurrentStep;

    public AbstractStepTaskGroupRunner(List<ITaskRunner> taskRunners) {
        mTaskRunners = taskRunners;
    }

    /** Initialize the TaskGroup runner */
    @Override
    public abstract void init(final ExperimentRunContext experimentRunContext);

    /** Start the TaskGroup runner */
    @Override
    public abstract void start(final ExperimentRunContext experimentRunContext);

    @Override
    public void execute(final ExperimentRunContext experimentRunContext) {
        switch (mCurrentStep) {
            case GET_TASK_GROUP_INDEX:
                if (mCurrentTaskGroupIndexPos == START_INDEX) {
                    // Fetch the starting position, must be implemented by the subclass
                    mCurrentTaskGroupIndexPos = initTaskGroupIndexPos();
                }
                else {
                    mCurrentTaskGroupIndexPos =
                            nextTaskGroupIndexPos(mCurrentTaskGroupIndexPos, mNumTaskGroupsExecuted);
                }
                break;

            case START_TASK_GROUP:
                // Get the current task group according to the index
                TaskGroup taskGroup = getCurTaskGroup(
                                        experimentRunContext.getExperiment());

                // Start the current task group
                taskGroup.start(experimentRunContext);
                break;

            case START_TASK_RUNNER:
                // [FIXME: how to choose the task runner?]
                ITaskRunner taskRunner = mTaskRunners
                                            .get(mCurrentTaskGroupIndexPos);

                // Initialize the current task runner
                taskRunner.init(experimentRunContext);

                // Apply the task runner to the current task group
                taskRunner.start(experimentRunContext, getCurTaskGroup(experimentRunContext.getExperiment()));
                break;

            case ENDED:
                experimentRunContext.getExperiment().complete(experimentRunContext);
                return;

            default:
                //[FIXME: exception here? proper logging?]
                System.out.println("TaskGroupRunner: exectute: ERROR: Bad step: " + mCurrentStep);
                break;
        }

        if (mAutoStep && mCurrentStep != TaskGroupStep.ENDED) {
            nextStep(experimentRunContext);
        }
    }

    @Override
    public boolean hasStep() {
        return (mCurrentStep != TaskGroupStep.ENDED);
    }

    @Override
    public void nextStep(ExperimentRunContext experimentRunContext) {
        mCurrentStep = _getNextStep(mCurrentStep);
        execute(experimentRunContext);
    }

    @Override
    public boolean isAutoStep() {
        return mAutoStep;
    }

    @Override
    public void setAutoStep(boolean autoStep) {
        mAutoStep = autoStep;
    }

    protected TaskGroupStep _getNextStep(TaskGroupStep step) {
        switch (step) {
            case NOT_STARTED:
                return TaskGroupStep.GET_TASK_GROUP_INDEX;

            case GET_TASK_GROUP_INDEX:
                return TaskGroupStep.START_TASK_GROUP;

            case START_TASK_GROUP:
                return TaskGroupStep.START_TASK_RUNNER;

            case START_TASK_RUNNER:
                if (mNumTaskGroupsExecuted >= mNumTaskGroupsToExecute) {
                    return TaskGroupStep.ENDED;
                }
                else {
                    return TaskGroupStep.GET_TASK_GROUP_INDEX;
                }

            default:
                System.out.println("TaskGroupRunner: _getNextStep: ERROR: Bad step: " + mCurrentStep);
                return TaskGroupStep.ENDED;
        }
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
    //[FIXME: do we need this?]
    protected abstract int initTaskGroupIndexPos();

    /** Set the next index index value */
    protected abstract int nextTaskGroupIndexPos(int currentTaskGroupIndexPos, int numTaskGroupsExecuted);
}
