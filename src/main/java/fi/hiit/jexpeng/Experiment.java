package fi.hiit.jexpeng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fi.hiit.jexpeng.event.ExperimentEventType;
import fi.hiit.jexpeng.event.ExperimentEvent;
import fi.hiit.jexpeng.event.IExperimentEventListener;
import fi.hiit.util.MetadataObject;


/**
 * Experiment
 *
 * The fundamental representation of an experiment.
 * <p>
 * An Experiment is a container for one or more TaskGroups.
 * <p>
 * An application using an Experiment object can register listeners for
 * events which are triggered during the life cycle of the Experiment.
 * <p>
 * An Experiment is typically used in conjunction with an ExperimentRunner.
 *
 * @author Konrad Markus <konker@luxvelocitas.com>
 *
 */
public class Experiment extends MetadataObject {
    protected List<TaskGroup> mTaskGroups;

    protected Set<IExperimentEventListener> mEventListeners;
    protected boolean mStarted;

    /**
     * Default constructor.
     * <p>
     * All constructors automatically assign a UUID to the object.
     */
    public Experiment() {
        _init();
    }

    /**
     * Convenience constructor taking an id
     * <p>
     * All constructors automatically assign a UUID to the object.
     *
     * @param id  the id of the object
     */
    public Experiment(long id) {
        _init();
        setId(id);
    }

    /**
     * Convenience constructor taking a name
     * <p>
     * All constructors automatically assign a UUID to the object.
     *
     * @param id  the id of the object
     */
    public Experiment(String name) {
        _init();
        setName(name);
    }

    /**
     * Common initialization tasks
     */
    private void _init() {
        setUuid();
        mTaskGroups = new ArrayList<TaskGroup>();
        mEventListeners = new CopyOnWriteArraySet<IExperimentEventListener>();
    }

    /* Life cycle methods {{{ */

    /**
     * Start the experiment
     * <p>
     * Triggers an EXPERIMENT_START event
     *
     * @param experimentRunContext  a context for this experiment run
     * @throws StaleExperimentRunContextException
     */
    public void start(ExperimentRunContext experimentRunContext) throws StaleExperimentRunContextException {
        if (experimentRunContext.isEnded()) {
            throw new StaleExperimentRunContextException();
        }

        mStarted = true;

        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new ExperimentEvent(ExperimentEventType.EXPERIMENT_START, experimentRunContext));
    }

    /**
     * Mark the experiment as completed.
     * <p>
     * Triggers an EXPERIMENT_END event.
     *
     * @param experimentRunContext  a context for this experiment run
     */
    public void complete(ExperimentRunContext experimentRunContext) {
        experimentRunContext.notifyRunContextEvent(new ExperimentEvent(ExperimentEventType.EXPERIMENT_END, experimentRunContext));
    }
    /* }}} */

    /* TaskGroup list methods {{{ */
    /**
     * Add a TaskGroup to this experiment.
     * <p>
     * TaskGroups can be added more than once if desired.
     *
     * @param taskGroup  the TaskGroup to add
     */
    public void addTaskGroup(TaskGroup taskGroup) {
        mTaskGroups.add(taskGroup);
    }

    /**
     * Remove a TaskGroup from the experiment.
     * <p>
     * Removes the first occurrence of the TaskGroup if the TaskGroup has been added multiple times.
     *
     * @param taskGroup  the TaskGroup to remove
     */
    public void removeTaskGroup(TaskGroup taskGroup) {
        mTaskGroups.remove(taskGroup);
    }

    /**
     * Get the number of TaskGroups in this Experiment
     *
     * @return
     */
    public int taskGroupSize() {
        return mTaskGroups.size();
    }

    /**
     * Remove all TaskGroups from this Experiment
     */
    public void taskGroupClear() {
        mTaskGroups.clear();
    }

    /**
     * Query if a TaskGroup exists in the Experiment.
     *
     * @param taskGroup  the TaskGroup to test for existence
     * @return
     */
    public boolean taskGroupContains(TaskGroup taskGroup) {
        return mTaskGroups.contains(taskGroup);
    }

    /**
     * Get the numerical index of the first occurrence of a TaskGroup
     *
     * @param taskGroup  the TaskGroup to find
     * @return the index of the first occurrence of the TaskGroup or -1 if it does not exist in the Experiment
     */
    public int indexOfTaskGroup(TaskGroup taskGroup) {
        return mTaskGroups.indexOf(taskGroup);
    }

    /**
     * Get the numerical index of the last occurrence of a TaskGroup
     *
     * @param taskGroup  the TaskGroup to find
     * @return the index of the last occurrence of the TaskGroup or -1 if it does not exist in the Experiment
     */
    public int lastIndexOfTaskGroup(TaskGroup taskGroup) {
        return mTaskGroups.lastIndexOf(taskGroup);
    }

    /**
     * Get a TaskGroup by numerical index
     *
     * @param i  the index
     * @return  the TaskGroup at the specified index
     * @throws IndexOutOfBoundsException
     */
    public TaskGroup getTaskGroup(int i) throws IndexOutOfBoundsException {
        return mTaskGroups.get(i);
    }

    /**
     * Set the TaskGroup at the given numerical index
     *
     * @param i  the index
     * @param taskGroup  the TaskGroup to set as the new item at the index
     */
    public void setTaskGroup(int i, TaskGroup taskGroup) {
        mTaskGroups.set(i, taskGroup);
    }

    /**
     * Get an Iterator for the TaskGroups in this Experiment
     *
     * @return
     */
    public Iterator<TaskGroup> taskGroupIterator() {
       return mTaskGroups.iterator();
    }
    /* }}} */

    /* Event methods {{{ */
    /**
     * Add an event listener for
     *
     * @param eventListener  the
     */
    public void addEventListener(IExperimentEventListener eventListener) {
        mEventListeners.add(eventListener);
    }

    public void removeEventListener(IExperimentEventListener eventListener) {
        mEventListeners.remove(eventListener);
    }

    public void notifyEvent(ExperimentEvent event) {
        for (IExperimentEventListener listener : mEventListeners) {
            listener.trigger(event);
        }
    }
    /* }}} */
}
