package fi.hiit.jexpeng.runner;

import fi.hiit.util.Util;


public class RandomOrderTaskRunner extends BaseTaskRunner implements ITaskRunner {
    @Override
    protected void initIndex() {
        // Initialize to default sequential index
        super.initIndex();

        // Shuffle the index
        Util.shuffleIntArrayInPlace(mIndex);
    }

    @Override
    protected int initIndexPos() {
        return 0;
    }

    @Override
    protected int nextIndexPos(int currentIndexPos, int tasksExecuted) {
        return currentIndexPos + 1;
    }
}
