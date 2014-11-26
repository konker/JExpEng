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


public class CsvDataSink implements IDataSink {
    private String mFileName;
    private CSVWriter mWriter;
    private final String mDataDir;

    public CsvDataSink(String dataDir) {
        mDataDir = dataDir;
    }

    public void init(ExperimentRunContext experimentRunContext) throws DataException {
        // Create a writer with filename composed of args
        try {
            mFileName = getFileName(mDataDir, experimentRunContext);
            mWriter = new CSVWriter(new FileWriter(mFileName));
        }
        catch (IOException ex) {
            throw new DataException(ex);
        }
    }

    public void write(Result result) throws DataException {
        // Write everything in the result
        String[] row = getRow(result);
        mWriter.writeNext(row);
    }

    public void close() throws DataException {
        try {
            mWriter.close();
        }
        catch (IOException ex) {
            throw new DataException(ex);
        }
    }

    protected String getFileName(String dataDir, ExperimentRunContext experimentRunContext) {
        String fileName =
                    "result-" +
                    experimentRunContext.getExperiment().getId() + "-" +
                    experimentRunContext.getSubject().getId() + "-" +
                    experimentRunContext.getRunId() + "-" +
                    (new Date()).getTime() +
                    ".csv";
        return (new File(mDataDir, fileName)).getAbsolutePath();
    }

    protected String[] getRow(Result result) {
        // Basic data
        List<String> row = new ArrayList<String>();
        row.add(String.valueOf(result.getTimestamp().getTime()));
        row.add(String.valueOf(result.getExperimentRunContext().getExperiment().getId()));
        row.add(result.getExperimentRunContext().getExperiment().getName());
        row.add(String.valueOf(result.getExperimentRunContext().getSubject().getId()));
        row.add(result.getExperimentRunContext().getSubject().getName());
        row.add(result.getExperimentRunContext().getRunId());
        row.add(String.valueOf(result.getTaskGroup().getId()));
        row.add(result.getTaskGroup().getName());
        row.add(String.valueOf(result.getTask().getId()));
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
