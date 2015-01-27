package fi.hiit.jexpeng.serialize.dummy;

import java.io.IOException;
import java.util.Iterator;

import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.serialize.AbstractTaskGroupSerializer;
import fi.hiit.jexpeng.serialize.ISerializationSink;
import fi.hiit.jexpeng.serialize.ITaskGroupSerializer;


public class DummyTaskGroupSerializer extends AbstractTaskGroupSerializer
                                      implements ITaskGroupSerializer
{
    @Override
    public void serialize(ISerializationSink sink, TaskGroup taskGroup) throws IOException {
        sink.write("TASKGROUP: [\n".getBytes());

        // Serialize metadata
        mMetadataSerializer.serialize(sink, taskGroup);

        // SERLIAZE TASKS
        Iterator<Task> iter =taskGroup.taskIterator();
        while (iter.hasNext()) {
            mTaskSerializer.serialize(sink, iter.next());
        }

        sink.write("]\n".getBytes());
    }
}
