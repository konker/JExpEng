package fi.hiit.jexpeng;

import java.util.Date;

import fi.hiit.util.DataBundle;
import fi.hiit.util.MetadataObject;


public class Result extends MetadataObject {
    protected ExperimentRunContext mExperimentRunContext;
    protected TaskGroup mTaskGroup;
    protected Task mTask;

    protected Date mTimestamp;
    protected DataBundle mData;

    public Result(ExperimentRunContext experimentRunContext, TaskGroup taskGroup, Task task) {
        super();
        mExperimentRunContext = experimentRunContext;
        mTaskGroup = taskGroup;
        mTask = task;
        mTimestamp = new Date();

        mData = new DataBundle();
    }

    public ExperimentRunContext getExperimentRunContext() {
        return mExperimentRunContext;
    }

    public TaskGroup getTaskGroup() {
        return mTaskGroup;
    }

    public void setTaskGroup(TaskGroup taskGroup) {
        mTaskGroup = taskGroup;
    }

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        mTask = task;
    }

    public Date getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Date timestamp) {
        mTimestamp = timestamp;
    }

    public DataBundle getData() {
        return mData;
    }
}
