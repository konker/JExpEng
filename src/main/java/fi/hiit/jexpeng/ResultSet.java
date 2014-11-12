package fi.hiit.jexpeng;

import java.util.ArrayList;
import java.util.List;

public class ResultSet {
    protected int mId;
    protected List<Result> mResultList;
    protected TaskGroup mTaskGroup;

    public ResultSet() {
        mResultList = new ArrayList<Result>();
    }

    public void add(Result result) {
        mResultList.add(result);
    }

    public List<Result> getResultList() {
        return mResultList;
    }
}
