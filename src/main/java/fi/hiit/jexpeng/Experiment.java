package fi.hiit.jexpeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.EventType;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.jexpeng.event.IRunContextEventListener;
import fi.hiit.jexpeng.runner.InvalidExperimentRunIdException;
import fi.hiit.util.MetadataObject;


public class Experiment extends MetadataObject {
    protected List<TaskGroup> mTaskGroups;
    protected Map<Integer, ResultSet> mResultSets;

    protected Set<IEventListener> mEventListeners;
    protected boolean mStarted;
    protected boolean mEnded;

    public Experiment() {
        mTaskGroups = new ArrayList<TaskGroup>();
        mResultSets = new HashMap<Integer, ResultSet>();

        mEventListeners = new CopyOnWriteArraySet<IEventListener>();
    }

    /* Life cycle methods {{{ */
    public void start(ExperimentRunContext experimentRunContext) throws InvalidExperimentRunIdException {
        if (mResultSets.containsKey(experimentRunContext.getRunId())) {
            throw new InvalidExperimentRunIdException();
        }

        // Initialize a ResultSet for this run
        mResultSets.put(experimentRunContext.getRunId(), new ResultSet());

        mStarted = true;

        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new Event(EventType.EXPERIMENT_START, experimentRunContext));
    }

    public void complete(ExperimentRunContext experimentRunContext) {
        experimentRunContext.notifyRunContextEvent(new Event(EventType.EXPERIMENT_END, experimentRunContext));
        mEnded = true;
    }

    public boolean isStarted() {
        return mStarted;
    }

    public boolean isEnded() {
        return mEnded;
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

    /* Result methods {{{ */
    public void addResult(Result result) throws InvalidExperimentRunIdException {
        if (!mResultSets.containsKey(result.getExperimentRunContext().getRunId())) {
            throw new InvalidExperimentRunIdException();
        }
        mResultSets.get(result.getExperimentRunContext().getRunId()).add(result);
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
