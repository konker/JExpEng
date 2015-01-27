package fi.hiit.jexpeng.serialize;

import java.io.IOException;

import fi.hiit.jexpeng.TaskGroup;


public interface ITaskGroupSerializer {
    public ITaskGroupSerializer init(IMetadataSerializer metadataSerializer, ITaskSerializer taskSerializer);
    public void serialize(ISerializationSink sink, TaskGroup taskGroup) throws IOException;
    public void close();
}
