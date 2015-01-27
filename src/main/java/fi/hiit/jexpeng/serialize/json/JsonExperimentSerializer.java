package fi.hiit.jexpeng.serialize.json;

import java.io.IOException;
import java.util.Iterator;

import org.json.JSONObject;

import fi.hiit.jexpeng.Experiment;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.serialize.AbstractExperimentSerializer;
import fi.hiit.jexpeng.serialize.ISerializationSink;


public class JsonExperimentSerializer extends AbstractExperimentSerializer {
    private JSONObject mRep;

    @Override
    public void serialize(ISerializationSink sink, Experiment experiment) throws IOException {
        mRep = new JSONObject();

        // Serialize metadata
        mMetadataSerializer.serialize(sink, experiment);

        // SERLIAZE TASK GROUPS
        Iterator<TaskGroup> iter = experiment.taskGroupIterator();
        while (iter.hasNext()) {
            mTaskGroupSerializer.serialize(sink, iter.next());
        }
    }
}
