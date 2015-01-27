package fi.hiit.jexpeng.serialize;


public class SerializationException extends Exception {
    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(Exception e) {
        super(e);
    }
}
