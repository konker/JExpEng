package fi.hiit.jexpeng;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fi.hiit.jexpeng.data.DataException;
import fi.hiit.jexpeng.data.IResultDataSink;
import fi.hiit.jexpeng.data.ISubjectDataSink;
import fi.hiit.jexpeng.event.ExperimentEvent;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public class ExperimentRunContext {
    protected final Experiment mExperiment;
    protected final Subject mSubject;
    protected final String mRunId;

    protected Set<IRunContextEventListener> mRunContextEventListeners;
    protected Set<IResultDataSink> mResultDataSinks;
    protected Set<ISubjectDataSink> mSubjectDataSinks;
    private boolean mEnded;

    public ExperimentRunContext(Experiment experiment, Subject subject, String runId) {
        mExperiment = experiment;
        mSubject = subject;
        mRunId = runId;
        mEnded = false;

        mRunContextEventListeners = new CopyOnWriteArraySet<IRunContextEventListener>();
        mResultDataSinks = new CopyOnWriteArraySet<IResultDataSink>();
        mSubjectDataSinks = new CopyOnWriteArraySet<ISubjectDataSink>();

        // Add a listener for the start and end of the experiment run
        addRunContextEventListener(new IRunContextEventListener() {
            @Override
            public void trigger(ExperimentEvent event) {
                switch (event.getEventType()) {
                    case EXPERIMENT_START:
                        _start();
                        break;

                    case EXPERIMENT_END:
                        _end();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    public boolean isEnded() {
        return mEnded;
    }

    public Experiment getExperiment() {
        return mExperiment;
    }

    public Subject getSubject() {
        return mSubject;
    }

    public String getRunId() {
        return mRunId;
    }

    public void addResultDataSink(IResultDataSink dataSink) throws DataException {
        dataSink.init(this);
        mResultDataSinks.add(dataSink);
    }

    public void removeResultDataSink(IResultDataSink dataSink) {
        mResultDataSinks.remove(dataSink);
    }

    public void addSubjectDataSink(ISubjectDataSink dataSink) throws DataException {
        dataSink.init(this);
        mSubjectDataSinks.add(dataSink);
    }

    public void removeSubjectDataSink(ISubjectDataSink dataSink) {
        mSubjectDataSinks.remove(dataSink);
    }

    public void writeSubjectData() throws DataException {
        for (ISubjectDataSink dataSink : mSubjectDataSinks) {
            dataSink.writeSubject(mSubject);
        }
    }

    public void addResult(Result result) throws DataException {
        for (IResultDataSink dataSink : mResultDataSinks) {
            dataSink.writeResult(result);
        }
    }

    public void addRunContextEventListener(IRunContextEventListener eventListener) {
        mRunContextEventListeners.add(eventListener);
    }

    public void removeRunContextEventListener(IRunContextEventListener eventListener) {
        mRunContextEventListeners.remove(eventListener);
    }

    public void notifyRunContextEvent(ExperimentEvent event) {
        // First notify the experiment for application listeners
        mExperiment.notifyEvent(event);

        // Notify system listeners
        for (IRunContextEventListener listener : mRunContextEventListeners) {
            listener.trigger(event);
        }
    }

    protected void _start() {
        //[XXX: nothing at the moment]
    }

    protected void _end() {
        mEnded = true;

        // Close any result data sinks
        for (IResultDataSink dataSink : mResultDataSinks) {
            try {
                dataSink.close();
            }
            catch (DataException ex) {
                //[FIXME: what to do with this?]
                ex.printStackTrace();
            }
        }

        // Close any subject data sinks
        for (ISubjectDataSink dataSink : mSubjectDataSinks) {
            try {
                dataSink.close();
            }
            catch (DataException ex) {
                //[FIXME: what to do with this?]
                ex.printStackTrace();
            }
        }
    }
}
