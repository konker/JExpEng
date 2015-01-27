package fi.hiit.jexpeng.serialize.json;

import fi.hiit.jexpeng.serialize.IMetadataSerializer;
import fi.hiit.jexpeng.serialize.ITaskSerializer;


public abstract class AbstractTaskSerializer implements ITaskSerializer {
    protected IMetadataSerializer mMetadataSerializer;

    @Override
    public ITaskSerializer init(IMetadataSerializer metadataSerializer) {
        mMetadataSerializer = metadataSerializer;
        return this;
    }

    @Override
    public void close() {
        //[NOP]
    }
}
