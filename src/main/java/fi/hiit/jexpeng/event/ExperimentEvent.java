package fi.hiit.jexpeng.event;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;


public class ExperimentEvent {
    private final ExperimentEventType mEventType;
    private final ExperimentRunContext mExperimentRunContext;
    private final TaskGroup mTaskGroup;
    private final Task mTask;

    public ExperimentEvent(ExperimentEventType eventType, ExperimentRunContext experimentRunContext, TaskGroup taskGroup, Task task) {
        mEventType = eventType;
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = taskGroup;
        mTask = task;
    }

    public ExperimentEvent(ExperimentEventType eventType, ExperimentRunContext experimentRunContext, TaskGroup taskGroup) {
        mEventType = eventType;
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = taskGroup;
        mTask = null;
    }

    public ExperimentEvent(ExperimentEventType eventType, ExperimentRunContext experimentRunContext) {
        mEventType = eventType;
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = null;
        mTask = null;
    }

    public ExperimentEventType getEventType() {
        return mEventType;
    }

    public ExperimentRunContext getExperimentRunContext() {
        return mExperimentRunContext;
    }

    public TaskGroup getTaskGroup() {
        return mTaskGroup;
    }

    public Task getTask() {
        return mTask;
    }
}
