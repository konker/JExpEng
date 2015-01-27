package fi.hiit.util;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;


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
    public static final String UUID_KEY = "uuid";
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final Set<String> SPECIAL_KEYS = new CopyOnWriteArraySet<String>();

    static {
        SPECIAL_KEYS.add(UUID_KEY);
        SPECIAL_KEYS.add(ID_KEY);
        SPECIAL_KEYS.add(NAME_KEY);
    }

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
