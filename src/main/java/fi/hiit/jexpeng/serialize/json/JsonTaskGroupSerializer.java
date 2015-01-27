package fi.hiit.jexpeng.serialize.json;

import java.io.IOException;

import fi.hiit.jexpeng.TaskGroup;
import fi.hiit.jexpeng.serialize.AbstractTaskGroupSerializer;
import fi.hiit.jexpeng.serialize.ISerializationSink;
import fi.hiit.jexpeng.serialize.ITaskGroupSerializer;

public class JsonTaskGroupSerializer extends AbstractTaskGroupSerializer
                                     implements ITaskGroupSerializer
{
    @Override
    public void serialize(ISerializationSink sink, TaskGroup taskGroup) throws IOException {
        //[TODO]
    }
}
