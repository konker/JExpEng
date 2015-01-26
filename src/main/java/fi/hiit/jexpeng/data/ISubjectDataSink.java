package fi.hiit.jexpeng.data;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Subject;


public interface ISubjectDataSink {
    public void init(ExperimentRunContext experimentRunContext) throws DataException;
    public void writeSubject(Subject subject) throws DataException;
    public void close() throws DataException;
}
