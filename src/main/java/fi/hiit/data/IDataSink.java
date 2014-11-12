package fi.hiit.data;

import fi.hiit.jexpeng.Experiment;
import fi.hiit.jexpeng.Result;
import fi.hiit.jexpeng.Subject;


public interface IDataSink {
    public void init(Experiment experiment, Subject subject, int runId);
    public void save(Result result);
    public void close();
}
