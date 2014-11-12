package fi.hiit.jexpeng.event;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;

public class Event {
    private final EventType mEventType;
    private final ExperimentRunContext mExperimentRunContext;
    private final TaskGroup mTaskGroup;
    private final Task mTask;

    public Event(EventType eventType, ExperimentRunContext experimentRunContext, TaskGroup taskGroup, Task task) {
        mEventType = eventType;
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = taskGroup;
        mTask = task;
    }

    public Event(EventType eventType, ExperimentRunContext experimentRunContext, TaskGroup taskGroup) {
        mEventType = eventType;
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = taskGroup;
        mTask = null;
    }

    public Event(EventType eventType, ExperimentRunContext experimentRunContext) {
        mEventType = eventType;
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = null;
        mTask = null;
    }

    public EventType getEventType() {
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
