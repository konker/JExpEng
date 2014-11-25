package fi.hiit.jexpeng.runner;

import java.util.List;

import fi.hiit.util.Util;


public class RandomOrderTaskGroupRunner extends BaseTaskGroupRunner implements ITaskGroupRunner {
    public RandomOrderTaskGroupRunner(List<ITaskRunner> taskRunners) {
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
    protected int initTaskGroupIndexPos() {
        return 0;
    }

    @Override
    protected int nextTaskGroupIndexPos(int currentTaskGroupIndexPos, int numTaskGroupsExecuted) {
        return currentTaskGroupIndexPos + 1;
    }
}
