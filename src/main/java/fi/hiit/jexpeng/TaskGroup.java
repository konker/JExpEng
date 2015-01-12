package fi.hiit.jexpeng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.EventType;
import fi.hiit.util.MetadataObject;


public class TaskGroup extends MetadataObject {
    protected List<Task> mTasks;

    public TaskGroup() {
        _init();
        setId(UUID.randomUUID().toString());
    }

    public TaskGroup(String id) {
        _init();
        setId(id);
    }

    private void _init() {
        mTasks = new ArrayList<Task>();
    }

    public void add(Task task) {
        mTasks.add(task);
    }

    public void remove(Task task) {
        mTasks.remove(task);
    }

    public int size() {
        return mTasks.size();
    }

    public boolean isEmpty() {
        return mTasks.isEmpty();
    }

    public void clear() {
        mTasks.clear();
    }

    public boolean contains(Task task) {
        return mTasks.contains(task);
    }

    public int indexOf(Task task) {
        return mTasks.indexOf(task);
    }

    public int lastIndexOf(Task task) {
        return mTasks.lastIndexOf(task);
    }

    public Task get(int i) {
        return mTasks.get(i);
    }

    public void set(int i, Task task) {
        mTasks.set(i, task);
    }

    public Iterator<Task> iterator() {
       return mTasks.iterator();
    }

    public void start(ExperimentRunContext experimentRunContext) {
        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new Event(EventType.TASK_GROUP_START, experimentRunContext, this));
    }

    public void complete(ExperimentRunContext experimentRunContext) {
        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new Event(EventType.TASK_GROUP_END, experimentRunContext, this));
    }
}
