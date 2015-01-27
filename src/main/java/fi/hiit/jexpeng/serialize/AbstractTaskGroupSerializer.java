package fi.hiit.jexpeng.serialize;



public abstract class AbstractTaskGroupSerializer implements ITaskGroupSerializer {
    protected IMetadataSerializer mMetadataSerializer;
    protected ITaskSerializer mTaskSerializer;

    @Override
    public ITaskGroupSerializer init(IMetadataSerializer metadataSerializer, ITaskSerializer taskSerializer) {
        mMetadataSerializer = metadataSerializer;
        mTaskSerializer = taskSerializer;
        return this;
    }

    @Override
    public void close() {
        //[NOP]
    }
}
