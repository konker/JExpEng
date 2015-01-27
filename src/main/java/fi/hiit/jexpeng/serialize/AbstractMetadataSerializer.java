package fi.hiit.jexpeng.serialize;


public abstract class AbstractMetadataSerializer implements IMetadataSerializer {

    @Override
    public IMetadataSerializer init() {
        return this;
    }

    @Override
    public void close() {
        //[NOP]
    }
}
