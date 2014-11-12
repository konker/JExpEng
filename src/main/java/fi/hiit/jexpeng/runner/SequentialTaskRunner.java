package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public class SequentialTaskRunner implements ITaskRunner {
    private int mCurrentIndex;
    private IRunContextEventListener mRunContextEventListener;

    public SequentialTaskRunner() {
        mCurrentIndex = 0;
        mRunContextEventListener = null;
    }

    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener = new IRunContextEventListener() {
            public void trigger(Event event) {
                if (event.getExperimentRunContext().getRunId() == experimentRunContext.getRunId()) {
                    switch (event.getEventType()) {
                        case TASK_END:
                            if (mCurrentIndex < event.getTaskGroup().size()) {
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
        mCurrentIndex = 0;
        execute(experimentRunContext, taskGroup);
    }

    public void execute(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup) {
        Task task = taskGroup.get(mCurrentIndex);
        mCurrentIndex++;

        task.start(experimentRunContext, taskGroup);
    }
}
