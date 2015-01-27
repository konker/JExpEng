package fi.hiit.jexpeng.serialize.dummy;

import java.io.IOException;
import java.util.Iterator;

import fi.hiit.jexpeng.Experiment;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.serialize.AbstractExperimentSerializer;
import fi.hiit.jexpeng.serialize.IExperimentSerializer;
import fi.hiit.jexpeng.serialize.ISerializationSink;


public class DummyExperimentSerializer extends AbstractExperimentSerializer
                                       implements IExperimentSerializer
{
    @Override
    public void serialize(ISerializationSink sink, Experiment experiment) throws IOException{
        sink.write("EXPERIMENT: [\n".getBytes());

        // Serialize metadata
        mMetadataSerializer.serialize(sink, experiment);

        // SERLIAZE TASK GROUPS
        Iterator<TaskGroup> iter = experiment.taskGroupIterator();
        while (iter.hasNext()) {
            mTaskGroupSerializer.serialize(sink, iter.next());
        }

        sink.write("]\n".getBytes());
    }
}
