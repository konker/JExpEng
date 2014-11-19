package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public abstract class BaseTaskGroupRunner implements ITaskGroupRunner {
    private final ITaskRunner mTaskRunner;

    protected int mCurrentIndexPos;
    protected int mTaskGroupsExecuted;
    protected int mNumTaskGroupsToExecute;
    protected int[] mIndex;
    protected IRunContextEventListener mRunContextEventListener;

    public BaseTaskGroupRunner(ITaskRunner taskRunner) {
        mTaskRunner = taskRunner;
    }

    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener= new IRunContextEventListener() {
            public void trigger(Event event) {
                if (event.getExperimentRunContext().getRunId() == experimentRunContext.getRunId()) {
                    switch (event.getEventType()) {
                        case TASK_GROUP_END:
                            mTaskGroupsExecuted++;

                            if (mTaskGroupsExecuted < mNumTaskGroupsToExecute) {
                                mCurrentIndexPos = nextIndexPos(mCurrentIndexPos, mTaskGroupsExecuted);
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
            }
        };
        experimentRunContext.addRunContextEventListener(mRunContextEventListener);
        mTaskRunner.init(experimentRunContext);
    }

    public void start(final ExperimentRunContext experimentRunContext) {
        mNumTaskGroupsToExecute = experimentRunContext.getExperiment().taskGroupSize();
        mTaskGroupsExecuted = 0;

        // Initialize the index, allow subclass to override this
        initIndex();

        // Fetch the starting position, must be implemented by the subclass
        mCurrentIndexPos = initIndexPos();

        execute(experimentRunContext);
    }

    public void execute(final ExperimentRunContext experimentRunContext) {
        /*[FIXME: should experiment be an argument here?]*/
        TaskGroup taskGroup = experimentRunContext.getExperiment().getTaskGroup(mIndex[mCurrentIndexPos]);

        taskGroup.start(experimentRunContext);
        mTaskRunner.start(experimentRunContext, taskGroup);
    }

    /** Initialize the index */
    protected void initIndex() {
        mIndex = new int[mNumTaskGroupsToExecute];

        // Initialize index to sequential order by default
        for (int i=0; i<mIndex.length; i++) {
            mIndex[i] = i;
        }
    }

    /** Set the next index index value */
    protected abstract int initIndexPos();

    /** Set the next index index value */
    protected abstract int nextIndexPos(int currentIndexPos, int tasksExecuted);
}
