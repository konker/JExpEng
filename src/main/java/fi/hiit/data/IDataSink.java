package fi.hiit.data;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Result;
import fi.hiit.jexpeng.Subject;


public interface IDataSink {
    public void init(ExperimentRunContext experimentRunContext) throws DataException;
    public void writeSubject(Subject subject) throws DataException;
    public void writeResult(Result result) throws DataException;
    public void close() throws DataException;
}
