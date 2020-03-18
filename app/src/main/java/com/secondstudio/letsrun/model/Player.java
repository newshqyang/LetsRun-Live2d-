package com.secondstudio.letsrun.model;

import android.os.Looper;
import android.widget.Toast;

import com.secondstudio.letsrun.GameActivity;
import com.secondstudio.letsrun.ui.AttributeView;
import com.secondstudio.letsrun.utility.WebUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Player {
    public static String mAccount;  //玩家账号
    private static String mName;       //玩家名称
    private static int mLevel = 1;      //等级
    private static String mDate;      //当前日期
    private static JSONArray mTasks;    //任务做没做数组
    private static JSONArray mTasksSize;    //任务列表做的数量


    public static JSONArray getTasksSize() {
        return mTasksSize;
    }

    public static void setTasksSize(JSONArray tasksSize) {
        mTasksSize = tasksSize;
    }

    private static GameActivity mActivity;
    public static void setActivity(GameActivity activity) {
        mActivity = activity;
    }


    /**
     * 加载属性界面所需玩家信息，并刷新属性界面
     * @throws JSONException
     */
    public static void Load_AvAndShow(final String account) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tmp = WebUtils.getInfo("av" , account);
                if (tmp == null) {
                    Looper.prepare();
                    Toast.makeText(mActivity, "ERROR:0001", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }
                try {
                    JSONObject info = new JSONObject(tmp);       //获取信息
                    mTasksSize = info.getJSONArray("tasksSize");
                    TaskList.Refresh_doneSize();
                    Looper.prepare();
                    AttributeView attributeView = new AttributeView(mActivity);
                    attributeView.show();
                    Looper.loop();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 加载主界面所需玩家信息，并刷新主界面
     */
    public static void Load_MV(final String account) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tmp = WebUtils.getInfo("load" , account);
                if (tmp == null) {
                    Looper.prepare();
                    Toast.makeText(mActivity, "ERROR:0001", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    return;
                }
                try {
                    JSONObject info = new JSONObject(tmp);       //获取信息
                    mName = info.getString("player_name");  //配置信息
                    mLevel = info.getInt("player_level");
                    mTasks = info.getJSONArray("tasks");
                    TaskList.Refresh_isDone();
                    mActivity.runOnUiThread(new Runnable() {     //刷新界面
                        @Override
                        public void run() {
                            mActivity.mAttributeLayout.Load();
                            mActivity.mTaskListLayout.refreshList();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static int getLevel() {   //根据任务次数判断等第
        return mLevel;
    }

    public static String getName() {
        return mName;
    }

    public static String getDate() {
        return mDate;
    }

    public static void setName(String name) {
        mName = name;
    }

    public static void setDate(String date) {
        mDate = date;
    }

    public static JSONArray getTasks() {
        return mTasks;
    }

    public static void setTasks(JSONArray tasks) {
        mTasks = tasks;
    }
}
