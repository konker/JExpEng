package fi.hiit.util;

import java.util.UUID;


/**
 * MetadataObject
 *
 * Represents an object which has metadata associtated with it.
 * <p>
 * Metadata is held in a DataBundle object.
 * <p>
 * Two metadata items are given special treatment, id and name.
 * Both of these are arbitrary Strings and both are opitonal.
 *
 * @author Konrad Markus <konker@luxvelocitas>
 *
 */
public abstract class MetadataObject {
    private static final String UUID_KEY = "uuid";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";

    protected DataBundle mMetadata;

    public MetadataObject() {
        mMetadata = new DataBundle();
    }

    /**
     * @return the DataBundle representing the metadata for this object
     */
    public DataBundle getMetadata() {
        return mMetadata;
    }

    /**
     * Get the special metadata field "uuid"
     *
     * @return the uuid
     */
    public String getUuid() {
        return mMetadata.getString(UUID_KEY);
    }

    /**
     * Generate a UUID and set the special metadata field "uuid"
     */
    public void setUuid() {
        mMetadata.putString(UUID_KEY, UUID.randomUUID().toString());
    }

    /**
     * Set the special metadata field "uuid"
     *
     * @param uuid  the new uuid
     */
    public void setUuid(String uuid) {
        mMetadata.putString(UUID_KEY, uuid);
    }

    /**
     * Get the special metadata field "id"
     *
     * @return the id
     */
    public long getId() {
        return mMetadata.getLong(ID_KEY);
    }

    /**
     * Set the special metadata field "id"
     *
     * @param id  the new id
     */
    public void setId(long id) {
        mMetadata.putLong(ID_KEY, id);
    }

    /**
     * Get the special metadata field "name"
     *
     * @return the name
     */
    public String getName() {
        return mMetadata.getString(NAME_KEY);
    }

    /**
     * Set the special metadata field "name"
     *
     * @param name  the new name
     */
    public void setName(String name) {
        mMetadata.putString(NAME_KEY, name);
    }
}
