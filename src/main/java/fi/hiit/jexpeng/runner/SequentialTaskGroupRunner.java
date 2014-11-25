package fi.hiit.jexpeng.runner;

import java.util.List;


public class SequentialTaskGroupRunner extends BaseTaskGroupRunner implements ITaskGroupRunner {

    public SequentialTaskGroupRunner(List<ITaskRunner> taskRunners) {
        super(taskRunners);
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
