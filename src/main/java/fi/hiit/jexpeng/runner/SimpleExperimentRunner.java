package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;


public class SimpleExperimentRunner implements IExperimentRunner {
    private final ITaskGroupRunner mTaskGroupRunner;

    public SimpleExperimentRunner(ITaskGroupRunner taskGroupRunner) {
        mTaskGroupRunner = taskGroupRunner;
    }

    public void init(ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException {
        mTaskGroupRunner.init(experimentRunContext);
    }

    public void start(ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException {
        execute(experimentRunContext);
        mTaskGroupRunner.start(experimentRunContext);
    }

    public void execute(ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException {
        experimentRunContext.getExperiment().start(experimentRunContext);
    }
}
