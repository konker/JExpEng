package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.StaleExperimentRunContext;

public interface IExperimentRunner {
    public void start(final ExperimentRunContext experimentRunContext) throws StaleExperimentRunContext;
    public void execute(final ExperimentRunContext experimentRunContext) throws StaleExperimentRunContext;
}
