package fi.hiit.jexpeng;

import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IEventListener;
import fi.hiit.jexpeng.runner.InvalidExperimentRunIdException;
import fi.hiit.jexpeng.runner.SimpleExperimentRunner;
import fi.hiit.jexpeng.runner.SequentialTaskGroupRunner;
import fi.hiit.jexpeng.runner.SequentialTaskRunner;


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
        for (int i=0; i<3; i++) {
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

                    default:
                        return;
                }
            }
        });

        experiment1.addEventListener(new IEventListener() {
            public void trigger(Event event) {
                switch (event.getEventType()) {
                    case TASK_GROUP_START:
                        System.out.println("\tTaskGroup Start: " + event.getTaskGroup().getName());
                        return;

                    case TASK_GROUP_END:
                        System.out.println("\tTaskGroup End: " + event.getTaskGroup().getName() + "\n");
                        return;

                    default:
                        return;
                }
            }
        });

        experiment1.addEventListener(new IEventListener() {
            public void trigger(Event event) {
                switch (event.getEventType()) {
                    case TASK_START:
                        System.out.println("\t\tTask: " + event.getTask().getName());
                        // HERE IS WHERE YOU WOULD ACTUALL PRESENT THE TASK, ETC
                        // ...
                        // create a dummy result
                        Result result = new Result(event.getExperimentRunContext(), event.getTaskGroup(), event.getTask());
                        result.getData().putString("dummy_result", "FOO");

                        // finish the task
                        try {
                            event.getTask().complete(result);
                        }
                        catch (InvalidExperimentRunIdException ex) {
                            ex.printStackTrace();
                        }
                        return;

                    default:
                        return;
                }
            }
        });

        // A Task runner which runs each the task sequentially
        SequentialTaskRunner taskRunner = new SequentialTaskRunner();

        // A TaskGroup runner which runs each task group sequentially
        SequentialTaskGroupRunner taskGroupRunner = new SequentialTaskGroupRunner(taskRunner);

        // A simple Experiment runner that starts the experiment and applies the task group runner
        SimpleExperimentRunner experimentRunner = new SimpleExperimentRunner(taskGroupRunner);

        // Create an ExperimentRunContext
        Subject subject1 = new Subject();
        subject1.setName("Subject 1");
        int runId1 = 23;
        ExperimentRunContext experimentRunContext = new ExperimentRunContext(experiment1, subject1, runId1);

        // Run the experiment
        try {
            experimentRunner.start(experimentRunContext);
        }
        catch (InvalidExperimentRunIdException ex) {
            ex.printStackTrace();
        }
    }
}
