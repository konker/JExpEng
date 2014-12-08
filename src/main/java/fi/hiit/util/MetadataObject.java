package fi.hiit.util;


public abstract class MetadataObject {
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";

    protected DataBundle mMetadata;

    public MetadataObject() {
        mMetadata = new DataBundle();
    }

    public DataBundle getMetadata() {
        return mMetadata;
    }

    public String getId() {
        return mMetadata.getString(ID_KEY);
    }

    public void setId(String id) {
        mMetadata.putString(ID_KEY, id);
    }

    public String getName() {
        return mMetadata.getString(NAME_KEY);
    }

    public void setName(String name) {
        mMetadata.putString(NAME_KEY, name);
    }
}
