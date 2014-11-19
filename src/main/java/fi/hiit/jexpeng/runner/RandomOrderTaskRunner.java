package fi.hiit.jexpeng.runner;

import fi.hiit.util.Util;


public class RandomOrderTaskRunner extends BaseTaskRunner implements ITaskRunner {
    @Override
    protected void initIndex() {
        // Initialize index to random order
        for (int i=0; i<mIndex.length; i++) {
            mIndex[i] = i;
        }

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
