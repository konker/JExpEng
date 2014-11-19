package fi.hiit.jexpeng.runner;



public class SequentialTaskGroupRunner extends BaseTaskGroupRunner implements ITaskGroupRunner {

    public SequentialTaskGroupRunner(ITaskRunner taskRunner) {
        super(taskRunner);
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
