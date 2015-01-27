package fi.hiit.jexpeng.serialize.json;

import java.io.IOException;

import fi.hiit.jexpeng.serialize.AbstractMetadataSerializer;
import fi.hiit.jexpeng.serialize.IMetadataSerializer;
import fi.hiit.jexpeng.serialize.ISerializationSink;
import fi.hiit.util.MetadataObject;

public class JsonMetadataSerializer extends AbstractMetadataSerializer
                                    implements IMetadataSerializer
{
    @Override
    public void serialize(ISerializationSink sink, MetadataObject metadata) throws IOException {
        //[TODO]
    }

}
