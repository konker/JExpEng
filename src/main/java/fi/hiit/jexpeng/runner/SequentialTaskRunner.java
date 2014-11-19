package fi.hiit.jexpeng.runner;



public class SequentialTaskRunner extends BaseTaskRunner implements ITaskRunner {
    @Override
    protected int initIndexPos() {
        return 0;
    }

    @Override
    protected int nextIndexPos(int currentIndexPos, int tasksExecuted) {
        return currentIndexPos + 1;
    }
}
