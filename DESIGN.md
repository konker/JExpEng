JExpEng Design
==============================================================================

Experiment/Field Test Framework in Java

- Provide a standardized (Java) framework for running experiments or field tests
- Should be flexible to cater for different requirements and scenarios
- Should be extensible for custom logging and metadata requirements


## Notes
https://facebook.github.io/planout/
- UniformChoice(choices=['foo', 'bar'])

- Is JExpEng backed by sqlite?
    - the design does seem to resemble a SQL schema
        - not sure if this is a good thing at this stage

- An experiment/tasks should be serializable in some way
    - XML?
    - JSON?
    - YAML?
    - and deserializable
    - serilaization format should allow for metadata, e.g. (x,y) coords of a visual representation
    - serilaization format should be protable, to e.g. web

- Do these various DataBundles need to have some kind of schema aspect to them?
    - something to enforce the integrity of items added to the list?
    - this would be a big overhead of course
    - could be an extension? ScheamDataBundle
    - what form would the schema be?
        - some kind of yaml description?
        - xml schema? schema-ng?
        - custom?
            - maybe not worth it, especially at first

- Event model
    - ExperimentStart
    - ExperimentEnd
    - BeforeTaskStart
    - TaskStart
    - BeforeTaskEnd
    - TaskEnd
    - TaskGroupStart
    - TaskGroupEnd
    - IterationStart
    - IterationEnd
    ...
    - ChooseNextTask
    - ChooseNextGroup


## Basics
- Experiment
    - Holds metadata and and one or more groups of tasks

- Task
    - Represents a unit of work within an experiment

- TaskList
    - A list of tasks to be performed

- TaskGroup
    - A logical group of tasks; e.g. phases, training mode, etc

- Subject
    - A subject performing the experiment

- Result
    - The result of performing a single task

- ResultSet
    - Represents a single run of the experiment by a subject

- DataSink
    - A sink for data, e.g. logging to a file


## Details
Experiment
    - int id
    - List<TaskGroup> taskGroups
    - List<ResultSet> resultSets
    - DataBundle metadata
        - String name
        - String location

Task
    - int id
    - DataBundle definition
        - String name
        - int number_to_show
        - String picture_to_show

TaskList
    - List<Task> tasks

TaskGroup
    - TaskList tasks
    - DataBundle metadata
        - String name

Subject
    - int id
    - DataBundle metadata
        - String name
        - int age, etc

Result
    - int id
    - Task task
    - TaskGroup group
    - Subject subject
    - Date timestamp
    - int iteration (?)
    - DataBundle taskContext
        - int previous_task_id (?)
        - int num_tasks_so_far (?)
        - ???
    - DataBundle data
        - (e.g. data gathered directly as a result of performing the task)
    - DataBundle extraData
        - (e.g. weather conditions when result was obtained)

ResultSet
    - int id
    - Subject subject
    - Date date // some kind of timestamp?
    - TaskGroup tasks
    - List<Result> results


## Events
- EXPERIMENT START
- EXPERIMENT END

- CHOOSE TASK GROUP
    - ideally the listener for this event would be able to affect the decision?
- TASK GROUP START
- TASK GROUP END

- CHOOSE TASK
    - ideally the listener for this event would be able to affect the decision?
- TASK START
- TASK END

- Should event listeners be able to affect the system?


## Iterations?
- Where should iterations fit into this?
- Should this be part of the core?
    - Or should it just be a runner?


## Operational
- e.g. 1
    - 2 TaskGroups, one for training, one for real
    - TaskGroups are presented in the order defined
    - Each TaskGroup has n > 1 Tasks
    - Tasks are performed in the order defined

- e.g. 2
    - 2 TaskGroups, one for training, one for real
    - TaskGroups are presented in the order defined
    - Each TaskGroup has n > 1 Tasks
    - Tasks are presented in a random order to each subject

- e.g. 3
    - 3 TaskGroups
    - TaskGroups are presented in a random order
    - Each TaskGroup has n > 1 Tasks
    - Tasks are presented in a random order to each subject

- e.g. 4
    - 3 TaskGroups, 1 for training
    - Training TaskGroup is presented first, then others are presented in a random order
    - Training tasks are presented in the order defined
    - Other Tasks are presented in a random order to each subject

- other e.g.
    - Ordering of tasks is fixed, but not in the order defined?
    - The choice of the next task depends on the result of the previous task?


## Data
- IDataSink
- CsvDataSink


## Serialization
- Need portable format for serializing an Experiment
    - e.g. to save and read from disk for usage
    - e.g. to allow for separate design tools
    - Android app will be a skeleton "experiment runner"
        - reads and executes a given experiment
        - Actual experiment will extend this

## Other
- Some way to save progress of a run in case of crash or something?
- A way to restart the run
    - proceed from where left off
    - start again from the begining
        - preserve original randomized order?


## Archived Notes

Result
    - Is taskId enough? really it should capture everything about
         the task and the context in which it was performed
            - keep a reference to the actual task object?
                - if a task can be shared by multiple task groups,
                  this is not enough!
                - should the task itself be self-contained in this sense?
                    - is a task actually like a class rather than an instance?
                        - i.e. do we need to make the distinction between a TaskTemplate and a Task?
                            - what is the actual distinction then?
                                - TaskTemplate defines the fundamental parameters of the task
                                - Task is an "instance" of a TaskTemplate, but with extra context data
                                        - instance means a reference to a TaskTemplate
                                        - extra context means:
                                            - TaskGroup?
                                            - Subject?
                                                - no, this is in Result
                                            - DataBundle of extra stuff?
                                                - (e.g. pre-condition which triggered task?, previous task?, ..)
                                - THEREFORE:
                                    - just put this extra context data into the Result!
                                    - no need for TaskTemplate

