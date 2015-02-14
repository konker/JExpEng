package fi.hiit.jexpeng.runner.group;

import fi.hiit.jexpeng.ExperimentRunContext;


public interface IStepTaskGroupRunner extends ITaskGroupRunner {
    @Override
    public void init(final ExperimentRunContext experimentRunContext);
    @Override
    public void start(final ExperimentRunContext experimentRunContext);
    @Override
    public void execute(final ExperimentRunContext experimentRunContext);

    public boolean hasStep();
    public void nextStep(ExperimentRunContext experimentRunContext);
    public boolean isAutoStep();
    public void setAutoStep(boolean autoStep);
}
