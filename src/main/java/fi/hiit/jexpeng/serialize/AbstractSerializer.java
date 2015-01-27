package fi.hiit.jexpeng.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import fi.hiit.jexpeng.Experiment;


public abstract class AbstractSerializer implements ISerializer {
    protected ISerializationSink mSink;

    protected IMetadataSerializer mMetadataSerializer;
    protected ITaskSerializer mTaskSerializer;
    protected ITaskGroupSerializer mTaskGroupSerializer;
    protected IExperimentSerializer mExperimentSerializer;

    @Override
    public ISerializer init(ISerializationSink sink) {
        mSink = sink;

        mMetadataSerializer.init();
        mTaskSerializer.init(mMetadataSerializer);
        mTaskGroupSerializer.init(mMetadataSerializer, mTaskSerializer);
        mExperimentSerializer.init(mMetadataSerializer, mTaskGroupSerializer);

        return this;
    }

    @Override
    public void serialize(Experiment experiment) throws IOException {
        mExperimentSerializer.serialize(mSink, experiment);
    }

    @Override
    public void close() {
        mExperimentSerializer.close();
        mTaskGroupSerializer.close();
        mTaskSerializer.close();
        mMetadataSerializer.close();
    }
}
