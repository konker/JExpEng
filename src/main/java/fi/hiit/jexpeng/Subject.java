package fi.hiit.jexpeng;

import fi.hiit.util.MetadataObject;


public class Subject extends MetadataObject {
    protected int mId;

    public Subject() {
        super();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
