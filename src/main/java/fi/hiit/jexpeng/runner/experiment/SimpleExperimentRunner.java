package fi.hiit.jexpeng.runner.experiment;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.StaleExperimentRunContextException;
import fi.hiit.jexpeng.runner.group.ITaskGroupRunner;


public class SimpleExperimentRunner implements IExperimentRunner {
    private final ITaskGroupRunner mTaskGroupRunner;
    private boolean mStepFlag;
    private boolean mAutoStep;

    public SimpleExperimentRunner(ITaskGroupRunner taskGroupRunner) {
        mTaskGroupRunner = taskGroupRunner;
        mAutoStep = false;
    }

    @Override
    public void start(ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException {
        // Initialize the task group runner
        mTaskGroupRunner.init(experimentRunContext);

        // Start the execution process
        execute(experimentRunContext);

        mStepFlag = true;
        if (mAutoStep) {
            nextStep(experimentRunContext);
        }
    }

    @Override
    public void execute(ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException {
        experimentRunContext
                .getExperiment()
                .start(experimentRunContext);
    }

    @Override
    public boolean hasStep() {
        return mStepFlag;
    }

    @Override
    public void nextStep(ExperimentRunContext experimentRunContext) {
        // Apply the task group runner to the the experiment task groups
        mTaskGroupRunner.start(experimentRunContext);

        mStepFlag = false;
    }

    @Override
    public boolean isAutoStep() {
        return mAutoStep;
    }

    @Override
    public void setAutoStep(boolean autoStep) {
        mAutoStep = autoStep;
    }
}
