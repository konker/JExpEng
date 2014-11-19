package fi.hiit.jexpeng.runner;



public class SequentialTaskRunner extends BaseTaskRunner implements ITaskRunner {
    @Override
    protected void initIndex() {
        // Initialize index to sequential order
        for (int i=0; i<mIndex.length; i++) {
            mIndex[i] = i;
        }
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
