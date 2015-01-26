package fi.hiit.jexpeng.data;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Result;


public interface IResultDataSink {
    public void init(ExperimentRunContext experimentRunContext) throws DataException;
    public void writeResult(Result result) throws DataException;
    public void close() throws DataException;
}
