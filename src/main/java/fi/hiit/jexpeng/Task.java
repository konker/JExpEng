package fi.hiit.jexpeng;

import fi.hiit.data.DataException;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.EventType;
import fi.hiit.util.DataBundle;
import fi.hiit.util.MetadataObject;


public abstract class Task extends MetadataObject {
    protected DataBundle mDefinition;

    public Task() {
        mDefinition = new DataBundle();
    }

    public DataBundle getDefinition() {
        return mDefinition;
    }

    public void start(ExperimentRunContext experimentRunContext, TaskGroup taskGroup) {
        // Broadcast the event to the run context
        experimentRunContext.notifyRunContextEvent(new Event(EventType.TASK_START, experimentRunContext, taskGroup, this));
    }

    public void complete(Result result) throws DataException {
        // Add the result to the experiment result set
        result.getExperimentRunContext().addResult(result);

        // Broadcast the event to the run context
        result.getExperimentRunContext().notifyRunContextEvent(new Event(EventType.TASK_END, result.getExperimentRunContext(), result.getTaskGroup(), this));
    }
}
