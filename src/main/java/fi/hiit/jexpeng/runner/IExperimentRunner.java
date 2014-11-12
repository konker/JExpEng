package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;

public interface IExperimentRunner {
    public void init(final ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException;
    public void start(final ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException;
    public void execute(final ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException;
}
