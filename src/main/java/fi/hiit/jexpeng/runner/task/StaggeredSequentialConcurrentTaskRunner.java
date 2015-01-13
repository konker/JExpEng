package fi.hiit.jexpeng.runner.task;



public class StaggeredSequentialConcurrentTaskRunner extends BaseConcurrentTaskRunner implements ITaskRunner {
    final int mStaggerDelayMs;

    public StaggeredSequentialConcurrentTaskRunner(int staggerDelayMs) {
        mStaggerDelayMs = staggerDelayMs;
    }

    @Override
    protected int initTaskIndexPos() {
        return 0;
    }

    @Override
    protected int nextTaskIndexPos(int currentTaskIndexPos, int numTasksExecuted) {
        return currentTaskIndexPos + 1;
    }

    @Override
    protected void startThreads() {
        // Start all the TaskThreads
        for (TaskThread t : mThreadGroup) {
            t.start();

            try {
                Thread.sleep(mStaggerDelayMs);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
