package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;


public class SimpleExperimentRunner implements IExperimentRunner {
    private final ITaskGroupRunner mTaskGroupRunner;

    public SimpleExperimentRunner(ITaskGroupRunner taskGroupRunner) {
        mTaskGroupRunner = taskGroupRunner;
    }

    public void start(ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException {
        // Initialize the task group runner
        mTaskGroupRunner.init(experimentRunContext);

        // Start the execution process
        execute(experimentRunContext);

        // Apply the task group runner to the the experiment task groups
        mTaskGroupRunner.start(experimentRunContext);
    }

    public void execute(ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException {
        experimentRunContext
                .getExperiment()
                .start(experimentRunContext);
    }
}
