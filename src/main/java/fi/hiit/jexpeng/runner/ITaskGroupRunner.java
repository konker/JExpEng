package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;


public interface ITaskGroupRunner {
    public void init(final ExperimentRunContext experimentRunContext);
    public void start(final ExperimentRunContext experimentRunContext);
    public void execute(final ExperimentRunContext experimentRunContext);
}
