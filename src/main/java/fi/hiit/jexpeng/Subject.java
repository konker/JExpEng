package fi.hiit.jexpeng;

import fi.hiit.util.DataBundle;
import fi.hiit.util.MetadataObject;


public class Subject extends MetadataObject {
    protected DataBundle mData;

    public Subject() {
        _init();
    }

    public Subject(long id) {
        _init();
        setId(id);
    }

    public Subject(String name) {
        _init();
        setName(name);
    }

    private void _init() {
        setUuid();
        mData = new DataBundle();
    }

    public DataBundle getData() {
        return mData;
    }
}
