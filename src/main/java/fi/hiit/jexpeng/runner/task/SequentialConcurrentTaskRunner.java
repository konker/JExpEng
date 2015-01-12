package fi.hiit.jexpeng.runner.task;



public class SequentialConcurrentTaskRunner extends BaseConcurrentTaskRunner implements ITaskRunner {
    @Override
    protected int initTaskIndexPos() {
        return 0;
    }

    @Override
    protected int nextTaskIndexPos(int currentTaskIndexPos, int numTasksExecuted) {
        return currentTaskIndexPos + 1;
    }
}
