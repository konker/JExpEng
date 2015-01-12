package fi.hiit.jexpeng.runner.experiment;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.StaleExperimentRunContextException;
import fi.hiit.jexpeng.runner.group.ITaskGroupRunner;


public class SimpleExperimentRunner implements IExperimentRunner {
    private final ITaskGroupRunner mTaskGroupRunner;

    public SimpleExperimentRunner(ITaskGroupRunner taskGroupRunner) {
        mTaskGroupRunner = taskGroupRunner;
    }

    @Override
    public void start(ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException {
        // Initialize the task group runner
        mTaskGroupRunner.init(experimentRunContext);

        // Start the execution process
        execute(experimentRunContext);

        // Apply the task group runner to the the experiment task groups
        mTaskGroupRunner.start(experimentRunContext);
    }

    @Override
    public void execute(ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException {
        experimentRunContext
                .getExperiment()
                .start(experimentRunContext);
    }
}
