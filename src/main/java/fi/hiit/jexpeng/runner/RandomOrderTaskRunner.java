package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;
import fi.hiit.util.Util;


public class RandomOrderTaskRunner implements ITaskRunner {
    protected int mCurrentIndexIndex;
    private int[] mIndex;
    private IRunContextEventListener mRunContextEventListener;

    public RandomOrderTaskRunner() {
        mRunContextEventListener = null;
    }

    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener = new IRunContextEventListener() {
            public void trigger(Event event) {
                if (event.getExperimentRunContext().getRunId() == experimentRunContext.getRunId()) {
                    switch (event.getEventType()) {
                        case TASK_END:
                            if (mCurrentIndexIndex < event.getTaskGroup().size()) {
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
    }

    public void start(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        mCurrentIndexIndex = 0;
        mIndex = new int[taskGroup.size()];
        for (int i=0; i<mIndex.length; i++) {
            mIndex[i] = i;
        }

        Util.shuffleIntArrayInPlace(mIndex);

        execute(experimentRunContext, taskGroup);
    }

    public void execute(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        Task task = taskGroup.get(mIndex[mCurrentIndexIndex]);
        mCurrentIndexIndex++;

        task.start(experimentRunContext, taskGroup);
    }
}
