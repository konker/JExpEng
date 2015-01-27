package fi.hiit.jexpeng.serialize.dummy;

import fi.hiit.jexpeng.serialize.AbstractSerializer;


public class DummySerializer extends AbstractSerializer {

    public DummySerializer() {
        mMetadataSerializer = new DummyMetadataSerializer();
        mExperimentSerializer = new DummyExperimentSerializer();
        mTaskGroupSerializer = new DummyTaskGroupSerializer();
        mTaskSerializer = new DummyTaskSerializer();
    }
}
