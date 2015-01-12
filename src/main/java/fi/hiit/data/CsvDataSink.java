package fi.hiit.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Result;
import fi.hiit.jexpeng.Subject;


public class CsvDataSink implements IDataSink {
    private ExperimentRunContext mExperimentRunContext;

    private String mSubjectFileName;
    private CSVWriter mSubjectWriter;
    private String mResultFileName;
    private CSVWriter mResultWriter;
    private final String mDataDir;

    public CsvDataSink(String dataDir) {
        mDataDir = dataDir;
    }

    @Override
    public void init(ExperimentRunContext experimentRunContext) throws DataException {
        mExperimentRunContext = experimentRunContext;

        // Create a writer with filename composed of args
        try {
            mSubjectFileName = getSubjectFileName(mDataDir);
            mSubjectWriter = new CSVWriter(new FileWriter(mSubjectFileName));

            mResultFileName = getResultFileName(mDataDir);
            mResultWriter = new CSVWriter(new FileWriter(mResultFileName));
        }
        catch (IOException ex) {
            throw new DataException(ex);
        }
    }

    @Override
    public void writeSubject(Subject subject) throws DataException {
        // Write everything in the result
        String[] row = getSubjectRow(subject);
        mSubjectWriter.writeNext(row);
    }

    @Override
    public void writeResult(Result result) throws DataException {
        // Write everything in the result
        String[] row = getResultRow(result);
        mResultWriter.writeNext(row);
    }

    @Override
    public void close() throws DataException {
        try {
            mSubjectWriter.close();
            mResultWriter.close();
        }
        catch (IOException ex) {
            throw new DataException(ex);
        }
    }

    protected String getSubjectFileName(String dataDir) {
        String fileName =
                    "subject-" +
                    mExperimentRunContext.getExperiment().getId() + "-" +
                    mExperimentRunContext.getSubject().getId() + "-" +
                    mExperimentRunContext.getRunId() + "-" +
                    (new Date()).getTime() +
                    ".csv";
        return (new File(mDataDir, fileName)).getAbsolutePath();
    }

    protected String getResultFileName(String dataDir) {
        String fileName =
                    "result-" +
                    mExperimentRunContext.getExperiment().getId() + "-" +
                    mExperimentRunContext.getSubject().getId() + "-" +
                    mExperimentRunContext.getRunId() + "-" +
                    (new Date()).getTime() +
                    ".csv";
        return (new File(mDataDir, fileName)).getAbsolutePath();
    }

    protected String[] getSubjectRow(Subject subject) {
        // Basic data
        List<String> row = new ArrayList<String>();
        row.add(mExperimentRunContext.getExperiment().getId());
        row.add(mExperimentRunContext.getExperiment().getName());
        row.add(mExperimentRunContext.getSubject().getId());
        row.add(mExperimentRunContext.getSubject().getName());
        row.add(mExperimentRunContext.getRunId());

        // Custom data fields
        for (String key : subject.getData().keySet()) {
            row.add(String.valueOf(subject.getData().get(key)));
        }

        String[] ret = new String[row.size()];
        row.toArray(ret);

        return ret;
    }

    protected String[] getResultRow(Result result) {
        // Basic data
        List<String> row = new ArrayList<String>();
        row.add(String.valueOf(result.getTimestamp().getTime()));
        row.add(mExperimentRunContext.getExperiment().getId());
        row.add(mExperimentRunContext.getExperiment().getName());
        row.add(mExperimentRunContext.getSubject().getId());
        row.add(mExperimentRunContext.getSubject().getName());
        row.add(mExperimentRunContext.getRunId());
        row.add(result.getTaskGroup().getId());
        row.add(result.getTaskGroup().getName());
        row.add(result.getTask().getId());
        row.add(result.getTask().getName());

        // Custom data fields
        for (String key : result.getData().keySet()) {
            row.add(String.valueOf(result.getData().get(key)));
        }

        String[] ret = new String[row.size()];
        row.toArray(ret);

        return ret;
    }
}
