package fi.hiit.jexpeng;

import java.util.ArrayList;
import java.util.List;

import fi.hiit.data.CsvDataSink;
import fi.hiit.data.DataException;
import fi.hiit.data.IDataSink;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.jexpeng.runner.IExperimentRunner;
import fi.hiit.jexpeng.runner.ITaskGroupRunner;
import fi.hiit.jexpeng.runner.ITaskRunner;
import fi.hiit.jexpeng.runner.RandomOrderTaskRunner;
import fi.hiit.jexpeng.runner.SequentialTaskGroupRunner;
import fi.hiit.jexpeng.runner.SequentialTaskRunner;
import fi.hiit.jexpeng.runner.SimpleExperimentRunner;


public class App {
    public static void main( String[] args ) {
        // Create an experiment
        Experiment experiment1 = new Experiment();
        experiment1.setId(1);
        experiment1.setName("Experiment 1");

        // Create a TaskGroup
        TaskGroup taskGroup1 = new TaskGroup();
        taskGroup1.setName("Training Tasks");

        // Create and add some tasks
        for (int i=0; i<3; i++) {
            Task t = new SimpleTask();
            t.setName("Training Task " + i);
            t.getDefinition().putInt("dummy_param", i);
            taskGroup1.add(t);
        }

        // Add the TaskGroup to the Experiment
        experiment1.addTaskGroup(taskGroup1);

        // Create a TaskGroup
        TaskGroup taskGroup2 = new TaskGroup();
        taskGroup2.setName("Real Tasks");

        // Create and add some tasks
        for (int i=0; i<6; i++) {
            Task t = new SimpleTask();
            t.setName("Real Task " + i);
            t.getDefinition().putInt("dummy_param", i);
            taskGroup2.add(t);
        }

        // Add the TaskGroup to the Experiment
        experiment1.addTaskGroup(taskGroup2);

        // Add some event handlers to the experiment
        experiment1.addEventListener(new IEventListener() {
            public void trigger(Event event) {
                switch (event.getEventType()) {
                    case EXPERIMENT_START:
                        System.out.println("Experiment Start: " + event.getExperimentRunContext().getExperiment().getName() + "\n");
                        return;

                    case EXPERIMENT_END:
                        System.out.println("Experiment End: " + event.getExperimentRunContext().getExperiment().getName());
                        //[TODO: print out result set]
                        return;

                    case TASK_GROUP_START:
                        System.out.println("\tTaskGroup Start: " + event.getTaskGroup().getName());
                        return;

                    case TASK_GROUP_END:
                        System.out.println("\tTaskGroup End: " + event.getTaskGroup().getName() + "\n");
                        return;

                    case TASK_START:
                        System.out.println("\t\tTask start: " + event.getTask().getName());
                        // HERE IS WHERE YOU WOULD ACTUALL PRESENT THE TASK, ETC
                        // ...
                        // create a dummy result
                        Result result = new Result(event.getExperimentRunContext(), event.getTaskGroup(), event.getTask());
                        result.getData().putString("dummy_result", "FOO");

                        // finish the task
                        try {
                            event.getTask().complete(result);
                        }
                        catch (DataException ex) {
                            ex.printStackTrace();
                        }
                        return;

                    case TASK_END:
                        System.out.println("\t\t\tTask end: " + event.getTask().getName());
                        return;

                    default:
                        return;
                }
            }
        });

        List<ITaskRunner> taskRunners = new ArrayList<ITaskRunner>();

        // A Task runner which runs each the task sequentially
        taskRunners.add(new SequentialTaskRunner());

        // A Task runner which runs tasks in a random order
        taskRunners.add(new RandomOrderTaskRunner());

        // A TaskGroup runner which runs each task group sequentially
        ITaskGroupRunner taskGroupRunner = new SequentialTaskGroupRunner(taskRunners);
        //ITaskGroupRunner taskGroupRunner = new RandomOrderTaskGroupRunner(taskRunner);

        // A simple Experiment runner that starts the experiment and applies the task group runner
        IExperimentRunner experimentRunner = new SimpleExperimentRunner(taskGroupRunner);

        // Create an ExperimentRunContext
        Subject subject1 = new Subject();
        subject1.setName("Subject 1");
        String runId1 = "run1";
        ExperimentRunContext experimentRunContext1 = new ExperimentRunContext(experiment1, subject1, runId1);

        Subject subject2 = new Subject();
        subject2.setName("Subject 2");
        String runId2 = "run2";
        ExperimentRunContext experimentRunContext2 = new ExperimentRunContext(experiment1, subject2, runId2);

        try {
            // Create a data sink
            IDataSink csvDataSink1 = new CsvDataSink("./");

            // Add a data sink to the run context
            experimentRunContext1.addDataSink(csvDataSink1);

            // Run the experiment
            experimentRunner.start(experimentRunContext1);

            // Create a data sink
            IDataSink csvDataSink2 = new CsvDataSink("./");

            // Add a data sink to the run context
            experimentRunContext2.addDataSink(csvDataSink2);

            // Try to run the experiment again
            experimentRunner.start(experimentRunContext2);

            // Try to run the experiment again (should fail)
            experimentRunner.start(experimentRunContext2);
        }
        catch (DataException ex1) {
            ex1.printStackTrace();
        }
        catch (StaleExperimentRunContext ex) {
            ex.printStackTrace();
        }
    }
}
