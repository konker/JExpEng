package fi.hiit.data;


public class DataException extends Exception {
    public DataException(String message) {
        super(message);
    }

    public DataException(Exception e) {
        super(e);
    }
}
