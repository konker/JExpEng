package fi.hiit.jexpeng.serialize.dummy;

import java.io.IOException;
import java.util.Set;

import fi.hiit.jexpeng.serialize.AbstractMetadataSerializer;
import fi.hiit.jexpeng.serialize.IMetadataSerializer;
import fi.hiit.jexpeng.serialize.ISerializationSink;
import fi.hiit.util.DataBundle;
import fi.hiit.util.MetadataObject;


public class DummyMetadataSerializer extends AbstractMetadataSerializer
                                     implements IMetadataSerializer
{
    @Override
    public void serialize(ISerializationSink sink, MetadataObject metadataObject) throws IOException {
        StringBuffer ret = new StringBuffer("METADATA: [\n");

        // Serialize metadata
        ret.append("\tUuid: ");
        ret.append(metadataObject.getUuid());
        ret.append("\n");
        ret.append("\tId: ");
        ret.append(String.valueOf(metadataObject.getId()));
        ret.append("\n");
        ret.append("\tName: ");
        ret.append(metadataObject.getName());
        ret.append("\n");

        // Custom fields
        DataBundle dataBundle = metadataObject.getMetadata();
        Set<String> keySet = dataBundle.keySet();
        keySet.removeAll(MetadataObject.SPECIAL_KEYS);
        for (String key : keySet) {
            ret.append("\t");
            ret.append(key);
            ret.append(": ");
            ret.append(String.valueOf(dataBundle.get(key)));
            ret.append("\n");
        }
        ret.append("]\n");

        sink.write(ret.toString().getBytes());
    }
}
