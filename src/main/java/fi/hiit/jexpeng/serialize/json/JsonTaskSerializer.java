package fi.hiit.jexpeng.serialize.json;

import java.io.IOException;

import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.serialize.ISerializationSink;
import fi.hiit.jexpeng.serialize.ITaskSerializer;

public class JsonTaskSerializer extends AbstractTaskSerializer
                                implements ITaskSerializer
{
    @Override
    public void serialize(ISerializationSink sink, Task task) throws IOException {
        //[TODO]
    }

}
