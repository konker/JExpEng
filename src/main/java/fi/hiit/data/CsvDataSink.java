package fi.hiit.data;

import au.com.bytecode.opencsv.CSVWriter;
import fi.hiit.jexpeng.Experiment;
import fi.hiit.jexpeng.Result;
import fi.hiit.jexpeng.Subject;


public class CsvDataSink implements IDataSink {
    private String mFileName;
    private CSVWriter mWriter;

    public void init(Experiment experiment, Subject subject, int runId) {
        // TODO Auto-generated method stub

    }

    public void save(Result result) {
        // TODO Auto-generated method stub

    }

    public void close() {
        // TODO Auto-generated method stub

    }

}
