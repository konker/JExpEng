package fi.hiit.jexpeng.runner;



public class SequentialTaskRunner extends BaseTaskRunner implements ITaskRunner {
    @Override
    protected int initTaskIndexPos() {
        return 0;
    }

    @Override
    protected int nextTaskIndexPos(int currentTaskIndexPos, int numTasksExecuted) {
        return currentTaskIndexPos + 1;
    }
}
