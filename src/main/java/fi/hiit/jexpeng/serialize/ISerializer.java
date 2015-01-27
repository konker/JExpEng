package fi.hiit.jexpeng.serialize;

import java.io.IOException;

import fi.hiit.jexpeng.Experiment;


public interface ISerializer {
    public ISerializer init(ISerializationSink sink);
    public void serialize(Experiment experiment) throws IOException;
    public void close();
}
