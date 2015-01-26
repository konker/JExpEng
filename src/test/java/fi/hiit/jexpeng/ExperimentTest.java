package fi.hiit.jexpeng;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import fi.hiit.jexpeng.event.ExperimentEvent;
import fi.hiit.jexpeng.event.IExperimentEventListener;


public class ExperimentTest {
    private static Experiment sExperiment;
    private static IExperimentEventListener sEventListener;
    private static int sCount;

    @Before
    public void init() {
        sExperiment = new Experiment("TextExperiment");
        sEventListener = new IExperimentEventListener() {
            @Override
            public void trigger(ExperimentEvent event) {
                sCount++;
            }
        };
        sCount = 0;

        assertNotEquals(sExperiment.mEventListeners, null);
        assertEquals(0, sExperiment.mEventListeners.size());
    }

    /*
    @Before
    public void beforeEachTest() {
        System.out.println("This is executed before each Test");
    }

    @After
    public void afterEachTest() {
        System.out.println("This is executed after each Test");
    }
    */

    @Test
    public void testAddEventListener() {
        sExperiment.addEventListener(sEventListener);
        assertEquals(1, sExperiment.mEventListeners.size());

        // Tests Set functionality
        sExperiment.addEventListener(sEventListener);
        assertEquals(1, sExperiment.mEventListeners.size());
    }

    @Test
    public void testRemoveEventListener() {
        sExperiment.removeEventListener(sEventListener);
        assertEquals(0, sExperiment.mEventListeners.size());
    }

    @Test
    public void testNotifyEvent() {
        sExperiment.addEventListener(sEventListener);
        assertEquals(1, sExperiment.mEventListeners.size());

        ExperimentEvent event = new ExperimentEvent(null, null);

        assertEquals(0, sCount);
        sExperiment.notifyEvent(event);
        assertEquals(1, sCount);
    }
}
