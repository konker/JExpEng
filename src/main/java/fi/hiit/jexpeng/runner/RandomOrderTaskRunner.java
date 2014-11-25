package fi.hiit.jexpeng.runner;

import fi.hiit.util.Util;


public class RandomOrderTaskRunner extends BaseTaskRunner implements ITaskRunner {
    @Override
    protected int[] initTaskIndex(int numTasksToExecute) {
        // Initialize to default sequential index
        int[] ret = super.initTaskIndex(numTasksToExecute);

        // Shuffle the index
        Util.shuffleIntArrayInPlace(ret);

        return ret;
    }

    @Override
    protected int initTaskIndexPos() {
        return 0;
    }

    @Override
    protected int nextTaskIndexPos(int currentTaskIndexPos, int numTasksExecuted) {
        return currentTaskIndexPos + 1;
    }
}
