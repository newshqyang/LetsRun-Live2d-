package com.secondstudio.letsrun.model;

import com.secondstudio.letsrun.GameActivity;
import com.secondstudio.letsrun.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private static GameActivity mActivity;
    public static void setActivity(GameActivity activity) {
        mActivity = activity;
        initialize();
    }

    /**
     * 任务类集合初始化，把本地不变资源都加载到集合中
     */
    private static void initialize() {
        mTasks = new ArrayList<>();
        String[] titles = mActivity.getResources().getStringArray(R.array.taskTitles);
        String[] descriptions = mActivity.getResources().getStringArray(R.array.taskDescriptions);
        for (int i=0;i<5;i++) {
            mTasks.add(new Task(i+1, titles[i], descriptions[i]));
        }
    }

    private static List<Task> mTasks;
    public static List<Task> getTasks(){
        return mTasks;
    }

    //刷新集合做的次数
    public static void Refresh_doneSize() throws JSONException {
        JSONArray tasksSize = Player.getTasksSize();
        for (int i=0;i<5;i++) {
            mTasks.get(i).setDoneSize(tasksSize.getJSONObject(i).getInt("size"));
        }
    }

    //刷新集合做没做
    public static void Refresh_isDone() throws JSONException {
        JSONArray tasks = Player.getTasks();
        for (int i=0;i<5;i++) {
            mTasks.get(i).setDone(tasks.getJSONObject(i).getInt("isDone"));
        }
    }

    /**
     * 获取没有完成的任务数量
     */
    public static int GetNotDoneSize() {
        int size = 0;
        for (int i=0;i<5;i++) {
            if (mTasks.get(i).getDone() == 0) {
                size++;
            }
        }
        return size;
    }

    //通过任务编号获取对象
//    public static Task getByTNo(int t_no) throws JSONException {
//        if (mTasks == null) {
//            System.out.println("刷新了");
//            Load_MV();
//        }
//        for (int i=0;i<mTasks.size();i++) {
//            if (t_no == mTasks.get(i).getT_No()) {
//                return mTasks.get(i);
//            }
//        }
//        return null;
//    }
}
