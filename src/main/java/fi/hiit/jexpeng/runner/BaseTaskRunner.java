package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public abstract class BaseTaskRunner implements ITaskRunner {
    protected int mCurrentIndexPos;
    protected int mTasksExecuted;
    protected int mNumTasksToExecute;
    protected int[] mIndex;
    protected IRunContextEventListener mRunContextEventListener;

    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener = new IRunContextEventListener() {
            public void trigger(Event event) {
                if (event.getExperimentRunContext().getRunId() == experimentRunContext.getRunId()) {
                    switch (event.getEventType()) {
                        case TASK_END:
                            mTasksExecuted++;

                            if (mTasksExecuted < mNumTasksToExecute) {
                                mCurrentIndexPos = nextIndexPos(mCurrentIndexPos, mTasksExecuted);
                                execute(experimentRunContext, event.getTaskGroup());
                            }
                            else {
                                // End of the TaskGroup
                                event.getTaskGroup().complete(experimentRunContext);
                            }

                        default:
                            return;
                    }
                }
            }
        };
        experimentRunContext.addRunContextEventListener(mRunContextEventListener);
        /*[FIXME: when should this listener be removed?]*/
    }

    public void start(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        mNumTasksToExecute = taskGroup.size();
        mTasksExecuted = 0;
        mIndex = new int[mNumTasksToExecute];

        initIndex();
        mCurrentIndexPos = initIndexPos();

        execute(experimentRunContext, taskGroup);
    }

    public void execute(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {

        taskGroup
            .get(mIndex[mCurrentIndexPos])
            .start(experimentRunContext, taskGroup);
    }

    /** Set the next index index value */
    protected abstract int initIndexPos();

    /** Set the next index index value */
    protected abstract int nextIndexPos(int currentIndexPos, int tasksExecuted);

    /** Initialize the index */
    protected abstract void initIndex();
}
