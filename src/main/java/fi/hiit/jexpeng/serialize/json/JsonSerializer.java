package fi.hiit.jexpeng.serialize.json;

import fi.hiit.jexpeng.serialize.AbstractSerializer;


public class JsonSerializer extends AbstractSerializer {

    public JsonSerializer() {
        mMetadataSerializer = new JsonMetadataSerializer();
        mExperimentSerializer = new JsonExperimentSerializer();
        mTaskGroupSerializer = new JsonTaskGroupSerializer();
        mTaskSerializer = new JsonTaskSerializer();
    }
}
