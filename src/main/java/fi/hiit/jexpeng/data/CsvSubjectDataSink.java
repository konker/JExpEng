package fi.hiit.jexpeng.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.Subject;


/*[TODO]
 * Write a header row?
 * Fix the order of columns?
 */
public class CsvSubjectDataSink implements ISubjectDataSink {
    private ExperimentRunContext mExperimentRunContext;

    private String mSubjectFileName;
    private CSVWriter mSubjectWriter;
    private final String mDataDir;

    public CsvSubjectDataSink(String dataDir) {
        mDataDir = dataDir;
    }

    @Override
    public void init(ExperimentRunContext experimentRunContext) throws DataException {
        mExperimentRunContext = experimentRunContext;

        // Create a writer with filename composed of args
        try {
            mSubjectFileName = getSubjectFileName(mDataDir);
            mSubjectWriter = new CSVWriter(new FileWriter(mSubjectFileName));
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
    public void close() throws DataException {
        try {
            mSubjectWriter.close();
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

    protected String[] getSubjectRow(Subject subject) {
        List<String> row = new ArrayList<String>();

        // Experiment data
        row.add(mExperimentRunContext.getExperiment().getUuid());
        row.add(String.valueOf(mExperimentRunContext.getExperiment().getId()));
        row.add(mExperimentRunContext.getExperiment().getName());

        // Subject data
        row.add(String.valueOf(mExperimentRunContext.getSubject().getId()));
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
}
