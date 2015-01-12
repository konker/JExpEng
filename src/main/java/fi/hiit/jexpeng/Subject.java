package fi.hiit.jexpeng;

import java.util.UUID;

import fi.hiit.util.DataBundle;
import fi.hiit.util.MetadataObject;


public class Subject extends MetadataObject {
    protected DataBundle mData;

    public Subject() {
        mData = new DataBundle();
        setId(UUID.randomUUID().toString());
    }

    public Subject(String id) {
        mData = new DataBundle();
        setId(id);
    }

    public DataBundle getData() {
        return mData;
    }
}
