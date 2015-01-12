package fi.hiit.jexpeng.runner.group;

import java.util.List;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.event.Event;
import fi.hiit.jexpeng.event.IRunContextEventListener;
import fi.hiit.jexpeng.runner.task.ITaskRunner;

/**
 * BaseTaskGroupRunner
 *
 * @author Konrad Markus <konker@luxvelocitas.com>
 *
 */
public abstract class BaseSyncTaskGroupRunner extends AbstractTaskGroupRunner implements ITaskGroupRunner {
    protected IRunContextEventListener mRunContextEventListener;

    public BaseSyncTaskGroupRunner(List<ITaskRunner> taskRunners) {
        super(taskRunners);
    }

    @Override
    public void init(final ExperimentRunContext experimentRunContext) {
        mRunContextEventListener = new IRunContextEventListener() {
            public void trigger(Event event) {
                //System.out.println("Kgroup: " + this + ": " + event.getEventType().toString());
                switch (event.getEventType()) {
                    case TASK_GROUP_END:
                        mNumTaskGroupsExecuted++;

                        if (mNumTaskGroupsExecuted < mNumTaskGroupsToExecute) {
                            mCurrentTaskGroupIndexPos =
                                    nextTaskGroupIndexPos(mCurrentTaskGroupIndexPos, mNumTaskGroupsExecuted);
                            execute(experimentRunContext);
                        }
                        else {
                            // End of the Experiment
                            experimentRunContext.getExperiment().complete(experimentRunContext);
                        }

                    default:
                        return;
                }
            }
        };

        experimentRunContext.addRunContextEventListener(mRunContextEventListener);
    }

    @Override
    public void start(final ExperimentRunContext experimentRunContext) {
        mNumTaskGroupsToExecute = experimentRunContext.getExperiment().taskGroupSize();
        mNumTaskGroupsExecuted = 0;

        // Initialize the index, allow subclass to override this
        mTaskGroupIndex = initTaskGroupIndex(mNumTaskGroupsToExecute);

        // Fetch the starting position, must be implemented by the subclass
        mCurrentTaskGroupIndexPos = initTaskGroupIndexPos();

        // Start the execution process
        execute(experimentRunContext);
    }
}
