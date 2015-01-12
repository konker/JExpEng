package fi.hiit.jexpeng;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fi.hiit.data.DataException;
import fi.hiit.data.IDataSink;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public class ExperimentRunContext {
    protected final Experiment mExperiment;
    protected final Subject mSubject;
    protected final String mRunId;

    protected Set<IRunContextEventListener> mRunContextEventListeners;
    protected Set<IDataSink> mDataSinks;
    private boolean mEnded;

    public ExperimentRunContext(Experiment experiment, Subject subject, String runId) {
        mExperiment = experiment;
        mSubject = subject;
        mRunId = runId;
        mEnded = false;

        mRunContextEventListeners = new CopyOnWriteArraySet<IRunContextEventListener>();
        mDataSinks = new CopyOnWriteArraySet<IDataSink>();

        // Add a listener for the start and end of the experiment run
        addRunContextEventListener(new IRunContextEventListener() {
            @Override
            public void trigger(Event event) {
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

    public void addDataSink(IDataSink dataSink) throws DataException {
        dataSink.init(this);
        mDataSinks.add(dataSink);
    }

    public void removeDataSink(IDataSink dataSink) {
        mDataSinks.remove(dataSink);
    }

    public void writeSubjectData() throws DataException {
        for (IDataSink dataSink : mDataSinks) {
            dataSink.writeSubject(mSubject);
        }
    }

    public void addResult(Result result) throws DataException {
        for (IDataSink dataSink : mDataSinks) {
            dataSink.writeResult(result);
        }
    }

    public void addRunContextEventListener(IRunContextEventListener eventListener) {
        mRunContextEventListeners.add(eventListener);
    }

    public void removeRunContextEventListener(IRunContextEventListener eventListener) {
        mRunContextEventListeners.remove(eventListener);
    }

    public void notifyRunContextEvent(Event event) {
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
        for (IDataSink dataSink : mDataSinks) {
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
