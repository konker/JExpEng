package fi.hiit.jexpeng.runner.group;

import java.util.List;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.runner.task.ITaskRunner;


public class SequentialSyncStepTaskGroupRunner extends BaseSyncStepTaskGroupRunner implements ITaskGroupRunner {

    public SequentialSyncStepTaskGroupRunner(List<ITaskRunner> taskRunners) {
        super(taskRunners);
    }

    @Override
    protected int initTaskGroupIndexPos() {
        return 0;
    }

    @Override
    protected int nextTaskGroupIndexPos(int currentTaskGroupIndexPos, int numTaskGroupsExecuted) {
        return currentTaskGroupIndexPos + 1;
    }
}
