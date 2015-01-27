package fi.hiit.jexpeng.serialize;

import java.io.IOException;


public interface ISerializationSink {
    public void write(byte[] bytes) throws IOException;
    public byte[] toByteArray();
}
