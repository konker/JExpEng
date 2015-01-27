package fi.hiit.jexpeng.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteArrayStreamSerializationSink implements ISerializationSink {
    private final ByteArrayOutputStream mRep;

    public ByteArrayStreamSerializationSink() {
        mRep = new ByteArrayOutputStream();
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        mRep.write(bytes);
    }

    @Override
    public byte[] toByteArray() {
        return mRep.toByteArray();
    }

}
