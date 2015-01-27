package fi.hiit.jexpeng.serialize;


public abstract class AbstractExperimentSerializer implements IExperimentSerializer {
    protected IMetadataSerializer mMetadataSerializer;
    protected ITaskGroupSerializer mTaskGroupSerializer;

    @Override
    public IExperimentSerializer init(IMetadataSerializer metadataSerializer, ITaskGroupSerializer taskGroupSerializer) {
        mMetadataSerializer = metadataSerializer;
        mTaskGroupSerializer = taskGroupSerializer;
        return this;
    }

    @Override
    public void close() {
        //[NOP]
    }
}
