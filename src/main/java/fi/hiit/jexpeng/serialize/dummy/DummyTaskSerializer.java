package fi.hiit.jexpeng.serialize.dummy;

import java.io.IOException;
import java.util.Set;

import fi.hiit.jexpeng.Task;
import fi.hiit.jexpeng.serialize.ISerializationSink;
import fi.hiit.jexpeng.serialize.ITaskSerializer;
import fi.hiit.jexpeng.serialize.json.AbstractTaskSerializer;
import fi.hiit.util.DataBundle;


public class DummyTaskSerializer extends AbstractTaskSerializer
                                 implements ITaskSerializer
{
    @Override
    public void serialize(ISerializationSink sink, Task task) throws IOException {
       sink.write("TASK: [\n".getBytes());

        // Serialize metadata
        mMetadataSerializer.serialize(sink, task);

        // Serialize definition
        StringBuffer ret = new StringBuffer();
        ret.append("DEFINITION: [\n");
        DataBundle dataBundle = task.getDefinition();
        Set<String> keySet = dataBundle.keySet();
        for (String key : keySet) {
            ret.append("\t");
            ret.append(key);
            ret.append(": ");
            ret.append(String.valueOf(dataBundle.get(key)));
            ret.append("\n");
        }
        ret.append("]\n");
        sink.write(ret.toString().getBytes());

        sink.write("]\n".getBytes());
    }
}
