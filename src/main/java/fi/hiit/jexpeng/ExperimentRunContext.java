package fi.hiit.jexpeng;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;


public class ExperimentRunContext {
    protected final Experiment mExperiment;
    protected final Subject mSubject;
    protected final int mRunId;

    protected Set<IRunContextEventListener> mRunContextEventListeners;

    public ExperimentRunContext(Experiment experiment, Subject subject, int runId) {
        mExperiment = experiment;
        mSubject = subject;
        mRunId = runId;

        mRunContextEventListeners = new CopyOnWriteArraySet<IRunContextEventListener>();
    }

    public Experiment getExperiment() {
        return mExperiment;
    }

    public Subject getSubject() {
        return mSubject;
    }

    public int getRunId() {
        return mRunId;
    }

    public void addRunContextEventListener(IRunContextEventListener eventListener) {
        mRunContextEventListeners.add(eventListener);
    }

    public void removeRunContextEventListener(IRunContextEventListener eventListener) {
        mRunContextEventListeners.remove(eventListener);
    }

    public void notifyRunContextEvent(Event event) {
        if (!mExperiment.isEnded()) {
            // First notify the experiment for application listeners
            mExperiment.notifyEvent(event);

            // Notify system listeners
            for (IRunContextEventListener listener : mRunContextEventListeners) {
                listener.trigger(event);
            }
        }
    }
}
