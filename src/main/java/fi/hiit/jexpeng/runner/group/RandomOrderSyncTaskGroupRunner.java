package fi.hiit.jexpeng.runner.group;

import java.util.List;

import fi.hiit.jexpeng.runner.task.ITaskRunner;
import fi.hiit.util.Util;


public class RandomOrderSyncTaskGroupRunner extends BaseSyncTaskGroupRunner implements ITaskGroupRunner {
    public RandomOrderSyncTaskGroupRunner(List<ITaskRunner> taskRunners) {
        super(taskRunners);
    }

    @Override
    protected int[] initTaskGroupIndex(int numTaskGroupsToExecute) {
        // Initialize to default sequential index
        int[] ret = super.initTaskGroupIndex(numTaskGroupsToExecute);

        // Shuffle the index
        Util.shuffleIntArrayInPlace(ret);

        return ret;
    }

    @Override
    protected int nextTaskGroupIndexPos(int currentTaskGroupIndexPos, int numTaskGroupsExecuted) {
        if (currentTaskGroupIndexPos == START_INDEX) {
            return 0;
        }
        return currentTaskGroupIndexPos + 1;
    }
}
