package fi.hiit.jexpeng.runner.group;

import java.util.List;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.event.ExperimentEvent;
import fi.hiit.jexpeng.event.IRunContextEventListener;
import fi.hiit.jexpeng.runner.task.ITaskRunner;

/**
 * BaseTaskGroupRunner
 *
 * @author Konrad Markus <konker@luxvelocitas.com>
 *
 */
public abstract class BaseSyncStepTaskGroupRunner extends AbstractStepTaskGroupRunner implements ITaskGroupRunner {
    protected IRunContextEventListener mRunContextEventListener;

    public BaseSyncStepTaskGroupRunner(List<ITaskRunner> taskRunners) {
        super(taskRunners);
    }

    @Override
    public void init(final ExperimentRunContext experimentRunContext) {
        mCurrentStep = TaskGroupStep.NOT_STARTED;

        mRunContextEventListener = new IRunContextEventListener() {
            @Override
            public void trigger(ExperimentEvent event) {
                //System.out.println("Kgroup: " + this + ": " + event.getEventType().toString());
                switch (event.getEventType()) {
                    case TASK_GROUP_END:
                        mNumTaskGroupsExecuted++;
                        nextStep(experimentRunContext);
                        break;

                    default:
                        return;
                }
            }
        };

        experimentRunContext.addRunContextEventListener(mRunContextEventListener);
    }

    @Override
    public void start(final ExperimentRunContext experimentRunContext) {
        mNumTaskGroupsToExecute = experimentRunContext.getExperiment().taskGroupSize();
        mNumTaskGroupsExecuted = 0;
        mCurrentTaskGroupIndexPos = START_INDEX;

        // Initialize the index, allow subclass to override this
        mTaskGroupIndex = initTaskGroupIndex(mNumTaskGroupsToExecute);

        nextStep(experimentRunContext);
    }
}
