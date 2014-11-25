package fi.hiit.jexpeng.runner;

import java.util.List;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;

/**
 * BaseTaskGroupRunner
 *
 * @author Konrad Markus <konker@luxvelocitas.com>
 *
 */
public abstract class BaseTaskGroupRunner implements ITaskGroupRunner {
    private final List<ITaskRunner> mTaskRunners;

    protected int mCurrentTaskGroupIndexPos;
    protected int mNumTaskGroupsExecuted;
    protected int mNumTaskGroupsToExecute;
    protected int[] mTaskGroupIndex;
    protected IRunContextEventListener mRunContextEventListener;

    public BaseTaskGroupRunner(List<ITaskRunner> taskRunners) {
        mTaskRunners = taskRunners;
    }

    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener = new IRunContextEventListener() {
            public void trigger(Event event) {
                //System.out.println("Kgroup: " + this + ": " + event.getEventType().toString());
                switch (event.getEventType()) {
                    case TASK_GROUP_END:
                        mNumTaskGroupsExecuted++;

                        if (mNumTaskGroupsExecuted < mNumTaskGroupsToExecute) {
                            mCurrentTaskGroupIndexPos =
                                    nextTaskGroupIndexPos(mCurrentTaskGroupIndexPos, mNumTaskGroupsExecuted);
                            execute(experimentRunContext);
                        }
                        else {
                            // End of the Experiment
                            experimentRunContext.getExperiment().complete(experimentRunContext);
                        }

                    default:
                        return;
                }
            }
        };

        experimentRunContext.addRunContextEventListener(mRunContextEventListener);
    }

    public void start(final ExperimentRunContext experimentRunContext) {
        mNumTaskGroupsToExecute = experimentRunContext.getExperiment().taskGroupSize();
        mNumTaskGroupsExecuted = 0;

        // Initialize the index, allow subclass to override this
        mTaskGroupIndex = initTaskGroupIndex(mNumTaskGroupsToExecute);

        // Fetch the starting position, must be implemented by the subclass
        mCurrentTaskGroupIndexPos = initTaskGroupIndexPos();

        // Start the execution process
        execute(experimentRunContext);
    }

    public void execute(final ExperimentRunContext experimentRunContext) {
        // Get the current task group according to the index
        TaskGroup taskGroup = experimentRunContext
                                    .getExperiment()
                                    .getTaskGroup(mTaskGroupIndex[mCurrentTaskGroupIndexPos]);

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
