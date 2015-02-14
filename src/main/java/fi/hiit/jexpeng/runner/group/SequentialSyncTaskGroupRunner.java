package fi.hiit.jexpeng.runner.group;

import java.util.List;

import fi.hiit.jexpeng.ExperimentRunContext;
import fi.hiit.jexpeng.runner.task.ITaskRunner;


public class SequentialSyncTaskGroupRunner extends BaseSyncTaskGroupRunner implements ITaskGroupRunner {

    public SequentialSyncTaskGroupRunner(List<ITaskRunner> taskRunners) {
        super(taskRunners);
    }

    @Override
    protected int nextTaskGroupIndexPos(int currentTaskGroupIndexPos, int numTaskGroupsExecuted) {
        if (currentTaskGroupIndexPos == START_INDEX) {
            return 0;
        }
        return currentTaskGroupIndexPos + 1;
    }
}
