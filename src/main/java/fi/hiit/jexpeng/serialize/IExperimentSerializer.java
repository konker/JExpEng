package fi.hiit.jexpeng.serialize;

import java.io.IOException;

import fi.hiit.jexpeng.Experiment;


public interface IExperimentSerializer {
    public IExperimentSerializer init(IMetadataSerializer metadataSerializer, ITaskGroupSerializer taskGroupSerializer);
    public void serialize(ISerializationSink sink, Experiment experiment) throws IOException;
    public void close();
}
