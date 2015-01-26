package fi.hiit.jexpeng;

import java.util.UUID;

import fi.hiit.jexpeng.data.DataException;
import fi.hiit.jexpeng.event.ExperimentEvent;
import fi.hiit.jexpeng.event.ExperimentEventType;
import fi.hiit.util.DataBundle;
import fi.hiit.util.MetadataObject;


public class Task extends MetadataObject {
    protected DataBundle mDefinition;

    public Task() {
        _init();
    }

    public Task(long id) {
        _init();
        setId(id);
    }

    public Task(String name) {
        _init();
        setName(name);
    }

    private void _init() {
        setUuid();
        mDefinition = new DataBundle();
    }

    public DataBundle getDefinition() {
        return mDefinition;
    }

    public void start(ExperimentRunContext experimentRunContext, TaskGroup taskGroup) {
        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new ExperimentEvent(ExperimentEventType.TASK_START, experimentRunContext, taskGroup, this));
    }

    public void complete(ExperimentRunContext experimentRunContext, TaskGroup taskGroup) {
        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new ExperimentEvent(ExperimentEventType.TASK_END, experimentRunContext, taskGroup, this));
    }

    public Task addResult(Result result) throws DataException {
        // Add the result to the experiment result set
        result.getExperimentRunContext().addResult(result);

        return this;
    }
}
