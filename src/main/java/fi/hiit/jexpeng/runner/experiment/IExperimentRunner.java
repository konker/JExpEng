package fi.hiit.jexpeng.runner.experiment;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.StaleExperimentRunContextException;


public interface IExperimentRunner {
    public void start(final ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException;
    public void execute(final ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException;
    public boolean hasStep();
    public void nextStep(ExperimentRunContext experimentRunContext);
    public boolean isAutoStep();
    public void setAutoStep(boolean autoStep);
}
