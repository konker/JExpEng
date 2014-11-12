package fi.hiit.jexpeng.runner;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.TaskGroup;

public interface ITaskRunner {
    public void init(final ExperimentRunContext experimentRunContext);
    public void start(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup);
    public void execute(final ExperimentRunContext experimentRunContext, final TaskGroup taskGroup);
}
