package fi.hiit.jexpeng.runner.task;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;


public class TaskThread extends Thread {
    private ExperimentRunContext mExperimentRunContext;
    private TaskGroup mTaskGroup;
    private final Task mTask;

    public TaskThread(ExperimentRunContext experimentRunContext, TaskGroup taskGroup, Task task) {
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = taskGroup;
        mTask = task;
    }

    @Override
    public void run() {
        mTask.start(mExperimentRunContext, mTaskGroup);
    }
}
