package fi.hiit.jexpeng.data.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Result;
import fi.hiit.jexpeng.data.DataException;
import fi.hiit.jexpeng.data.IResultDataSink;


/*[TODO]
 * Write a header row?
 * Fix the order of columns?
 */
public class CsvResultDataSink implements IResultDataSink {
    private ExperimentRunContext mExperimentRunContext;

    private String mResultFileName;
    private CSVWriter mResultWriter;
    private final String mDataDir;

    public CsvResultDataSink(String dataDir) {
        mDataDir = dataDir;
    }

    @Override
    public void init(ExperimentRunContext experimentRunContext) throws DataException {
        mExperimentRunContext = experimentRunContext;

        // Create a writer with filename composed of args
        try {
            mResultFileName = getResultFileName(mDataDir);
            mResultWriter = new CSVWriter(new FileWriter(mResultFileName));
        }
        catch (IOException ex) {
            throw new DataException(ex);
        }
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
            mResultWriter.close();
        }
        catch (IOException ex) {
            throw new DataException(ex);
        }
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

    protected String[] getResultRow(Result result) {
        List<String> row = new ArrayList<String>();

        // Basic data
        row.add(String.valueOf(result.getTimestamp().getTime()));
        row.add(mExperimentRunContext.getRunId());

        // Experiment data
        row.add(mExperimentRunContext.getExperiment().getUuid());
        row.add(String.valueOf(mExperimentRunContext.getExperiment().getId()));
        row.add(mExperimentRunContext.getExperiment().getName());

        // Subject data
        row.add(mExperimentRunContext.getSubject().getUuid());
        row.add(String.valueOf(mExperimentRunContext.getSubject().getId()));
        row.add(mExperimentRunContext.getSubject().getName());

        // TaskGroup data
        row.add(result.getTaskGroup().getUuid());
        row.add(String.valueOf(result.getTaskGroup().getId()));
        row.add(result.getTaskGroup().getName());

        // Task data
        row.add(result.getTask().getUuid());
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
