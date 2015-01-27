package fi.hiit.jexpeng.serialize;

import java.io.IOException;

import fi.hiit.util.MetadataObject;


public interface IMetadataSerializer {
    public IMetadataSerializer init();
    public void serialize(ISerializationSink sink, MetadataObject metadata) throws IOException;
    public void close();
}
