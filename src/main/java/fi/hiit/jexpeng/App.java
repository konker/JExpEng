package fi.hiit.jexpeng;

import java.util.ArrayList;
import java.util.List;

import fi.hiit.data.CsvDataSink;
import fi.hiit.data.DataException;
import fi.hiit.data.IDataSink;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.jexpeng.runner.experiment.IExperimentRunner;
import fi.hiit.jexpeng.runner.experiment.SimpleExperimentRunner;
import fi.hiit.jexpeng.runner.group.ITaskGroupRunner;
import fi.hiit.jexpeng.runner.group.SequentialSyncTaskGroupRunner;
import fi.hiit.jexpeng.runner.task.ITaskRunner;
import fi.hiit.jexpeng.runner.task.RandomOrderConcurrentTaskRunner;
import fi.hiit.jexpeng.runner.task.RandomOrderSyncTaskRunner;
import fi.hiit.jexpeng.runner.task.SequentialConcurrentTaskRunner;
import fi.hiit.jexpeng.runner.task.SequentialSyncTaskRunner;


public class App {
    public static void main( String[] args ) {
        // Create an experiment
        Experiment experiment1 = new Experiment("Exp1");
        experiment1.setName("Experiment 1");

        // Create a TaskGroup
        TaskGroup taskGroup1 = new TaskGroup("tg-t1");
        taskGroup1.setName("Training Tasks");

        // Create and add some tasks
        for (int i=0; i<3; i++) {
            Task t = new Task("t" + i);
            t.setName("Training Task " + i);
            t.getDefinition().putInt("dummy_param", i);
            taskGroup1.add(t);
        }

        // Add the TaskGroup to the Experiment
        experiment1.addTaskGroup(taskGroup1);

        // Create a TaskGroup
        TaskGroup taskGroup2 = new TaskGroup("tg-r1");
        taskGroup2.setName("Real Tasks");

        // Create and add some tasks
        for (int i=0; i<6; i++) {
            Task t = new Task("r" + i);
            t.setName("Real Task " + i);
            t.getDefinition().putInt("dummy_param", i);
            taskGroup2.add(t);
        }

        // Add the TaskGroup to the Experiment
        experiment1.addTaskGroup(taskGroup2);

        // Add some event handlers to the experiment
        experiment1.addEventListener(new IEventListener() {
            @Override
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
                        System.out.println("\t\tTask start(" + Thread.currentThread().getId() + "): " + event.getTask().getName());
                        // HERE IS WHERE YOU WOULD ACTUALL PRESENT THE TASK, ETC
                        // ...
                        // create a dummy result
                        Result result = new Result(event.getExperimentRunContext(), event.getTaskGroup(), event.getTask());
                        result.getData().putString("dummy_result", "FOO");

                        // finish the task
                        try {

                            event.getTask()
                                    .addResult(result)
                                    .complete(event.getExperimentRunContext(), event.getTaskGroup());
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

        // -----------------------------------------------------------------------------
        // Set up an experiment runner
        List<ITaskRunner> taskRunners1 = new ArrayList<ITaskRunner>();

        // A Task runner which runs each the task sequentially
        taskRunners1.add(new SequentialSyncTaskRunner());

        // A Task runner which runs tasks in a random order
        taskRunners1.add(new RandomOrderSyncTaskRunner());

        // A TaskGroup runner which runs each task group sequentially
        ITaskGroupRunner taskGroupRunner1 = new SequentialSyncTaskGroupRunner(taskRunners1);
        //ITaskGroupRunner taskGroupRunner = new RandomOrderTaskGroupRunner(taskRunner);

        // A simple Experiment runner that starts the experiment and applies the task group runner
        IExperimentRunner experimentRunner1 = new SimpleExperimentRunner(taskGroupRunner1);

        // -----------------------------------------------------------------------------
        // Set up an experiment runner 2
        List<ITaskRunner> taskRunners2 = new ArrayList<ITaskRunner>();

        // A Task runner which runs each the task sequentially
        taskRunners2.add(new SequentialConcurrentTaskRunner());

        // A Task runner which runs tasks in a random order
        taskRunners2.add(new RandomOrderConcurrentTaskRunner());

        // A TaskGroup runner which runs each task group sequentially
        ITaskGroupRunner taskGroupRunner2 = new SequentialSyncTaskGroupRunner(taskRunners2);

        // A simple Experiment runner that starts the experiment and applies the task group runner
        IExperimentRunner experimentRunner2 = new SimpleExperimentRunner(taskGroupRunner2);

        // Create an ExperimentRunContext
        Subject subject1 = new Subject("subject1");
        subject1.setName("Subject 1");
        subject1.getData().putString("foo", "bar1");
        String runId1 = "run1";
        ExperimentRunContext experimentRunContext1 = new ExperimentRunContext(experiment1, subject1, runId1);

        Subject subject2 = new Subject("subject2");
        subject2.setName("Subject 2");
        subject2.getData().putString("foo", "bar2");
        String runId2 = "run2";
        ExperimentRunContext experimentRunContext2 = new ExperimentRunContext(experiment1, subject2, runId2);

        Subject subject3 = new Subject();
        subject3.setName("Subject 3");
        String runId3 = "run3";
        ExperimentRunContext experimentRunContext3 = new ExperimentRunContext(experiment1, subject3, runId3);

        try {
            // Create a data sink
            IDataSink csvDataSink1 = new CsvDataSink("./");

            // Add a data sink to the run context
            experimentRunContext1.addDataSink(csvDataSink1);

            // Write Subject data to the data sink
            experimentRunContext1.writeSubjectData();

            // Run the experiment
            experimentRunner1.start(experimentRunContext1);

            // Create a data sink
            IDataSink csvDataSink2 = new CsvDataSink("./");

            // Add a data sink to the run context
            experimentRunContext2.addDataSink(csvDataSink2);

            // Write Subject data to the data sink
            experimentRunContext2.writeSubjectData();

            // Try to run the experiment again
            experimentRunner1.start(experimentRunContext2);

            // Try to run the experiment again (should fail)
            experimentRunner2.start(experimentRunContext3);

            // Try to run the experiment again (should fail)
            experimentRunner1.start(experimentRunContext2);
        }
        catch (DataException ex1) {
            ex1.printStackTrace();
        }
        catch (StaleExperimentRunContextException ex) {
            ex.printStackTrace();
        }
    }
}
