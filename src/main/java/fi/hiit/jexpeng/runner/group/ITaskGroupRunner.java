package fi.hiit.jexpeng.runner.group;

import fi.hiit.jexpeng.ExperimentRunContext;


public interface ITaskGroupRunner {
    public void init(final ExperimentRunContext experimentRunContext);
    public void start(final ExperimentRunContext experimentRunContext);
    public void execute(final ExperimentRunContext experimentRunContext);

    public boolean hasStep();
    public void nextStep(ExperimentRunContext experimentRunContext);
    public boolean isAutoStep();
    public void setAutoStep(boolean autoStep);
}
