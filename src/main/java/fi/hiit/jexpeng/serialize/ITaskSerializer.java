package fi.hiit.jexpeng.serialize;

import java.io.IOException;

import fi.hiit.jexpeng.Task;


public interface ITaskSerializer {
    public ITaskSerializer init(IMetadataSerializer metadataSerializer);
    public void serialize(ISerializationSink sink, Task task) throws IOException;
    public void close();
}
