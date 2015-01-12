package fi.hiit.jexpeng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.EventType;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.util.MetadataObject;


public class Experiment extends MetadataObject {
    protected List<TaskGroup> mTaskGroups;

    protected Set<IEventListener> mEventListeners;
    protected boolean mStarted;

    public Experiment() {
        _init();
        setId(UUID.randomUUID().toString());
    }

    public Experiment(String id) {
        _init();
        setId(id);
    }

    private void _init() {
        mTaskGroups = new ArrayList<TaskGroup>();
        mEventListeners = new CopyOnWriteArraySet<IEventListener>();
    }

    /* Life cycle methods {{{ */
    public void start(ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException {
        if (experimentRunContext.isEnded()) {
            throw new StaleExperimentRunContextException();
        }

        mStarted = true;

        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new Event(EventType.EXPERIMENT_START, experimentRunContext));
    }

    public void complete(ExperimentRunContext experimentRunContext) {
        experimentRunContext.notifyRunContextEvent(new Event(EventType.EXPERIMENT_END, experimentRunContext));
    }
    /* }}} */

    /* TaskGroup list methods {{{ */
    public void addTaskGroup(TaskGroup taskGroup) {
        mTaskGroups.add(taskGroup);
    }

    public void removeTaskGroup(Task task) {
        mTaskGroups.remove(task);
    }

    public int taskGroupSize() {
        return mTaskGroups.size();
    }

    public void taskGroupClear() {
        mTaskGroups.clear();
    }

    public boolean taskGroupContains(Task task) {
        return mTaskGroups.contains(task);
    }

    public int indexOfTaskGroup(Task task) {
        return mTaskGroups.indexOf(task);
    }

    public int lastIndexOfTaskGroup(Task task) {
        return mTaskGroups.lastIndexOf(task);
    }

    public TaskGroup getTaskGroup(int i) {
        return mTaskGroups.get(i);
    }

    public void setTaskGroup(int i, TaskGroup taskGroup) {
        mTaskGroups.set(i, taskGroup);
    }

    public Iterator<TaskGroup> taskGroupIterator() {
       return mTaskGroups.iterator();
    }
    /* }}} */

    /* Event methods {{{ */
    public void addEventListener(IEventListener eventListener) {
        mEventListeners.add(eventListener);
    }

    public void removeEventListener(IEventListener eventListener) {
        mEventListeners.remove(eventListener);
    }

    public void notifyEvent(Event event) {
        for (IEventListener listener : mEventListeners) {
            listener.trigger(event);
        }
    }
    /* }}} */
}
