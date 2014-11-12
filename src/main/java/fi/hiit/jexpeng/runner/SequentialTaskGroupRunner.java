package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public class SequentialTaskGroupRunner implements ITaskGroupRunner {
    private final ITaskRunner mTaskRunner;
    private int mCurrentIndex;
    private IRunContextEventListener mEventListener;

    public SequentialTaskGroupRunner(ITaskRunner taskRunner) {
        mTaskRunner = taskRunner;
        mCurrentIndex = 0;
        mEventListener = null;
    }

    public void init(final ExperimentRunContext experimentRunContext) {
        mEventListener = new IRunContextEventListener() {
            public void trigger(Event event) {
                if (event.getExperimentRunContext().getRunId() == experimentRunContext.getRunId()) {
                    switch (event.getEventType()) {
                        case TASK_GROUP_END:
                            if (mCurrentIndex < experimentRunContext.getExperiment().taskGroupSize()) {
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
        experimentRunContext.addRunContextEventListener(mEventListener);
        mTaskRunner.init(experimentRunContext);
    }

    public void start(final ExperimentRunContext experimentRunContext) {
        mCurrentIndex = 0;
        execute(experimentRunContext);
    }

    public void execute(final ExperimentRunContext experimentRunContext) {
        TaskGroup taskGroup = experimentRunContext.getExperiment().getTaskGroup(mCurrentIndex);
        mCurrentIndex++;

        taskGroup.start(experimentRunContext);
        mTaskRunner.start(experimentRunContext, taskGroup);
    }
}
