package fi.hiit.jexpeng;

import java.util.UUID;

import fi.hiit.jexpeng.data.DataException;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.EventType;
import fi.hiit.util.DataBundle;
import fi.hiit.util.MetadataObject;


public class Task extends MetadataObject {
    protected DataBundle mDefinition;

    public Task() {
        _init();
        setId(UUID.randomUUID().toString());
    }

    public Task(String id) {
        _init();
        setId(id);
    }

    private void _init() {
        mDefinition = new DataBundle();
    }

    public DataBundle getDefinition() {
        return mDefinition;
    }

    public void start(ExperimentRunContext experimentRunContext, TaskGroup taskGroup) {
        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new Event(EventType.TASK_START, experimentRunContext, taskGroup, this));
    }

    public void complete(ExperimentRunContext experimentRunContext, TaskGroup taskGroup) {
        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new Event(EventType.TASK_END, experimentRunContext, taskGroup, this));
    }

    public Task addResult(Result result) throws DataException {
        // Add the result to the experiment result set
        result.getExperimentRunContext().addResult(result);

        return this;
    }
}
