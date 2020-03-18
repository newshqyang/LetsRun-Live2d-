package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.secondstudio.letsrun.model.Player;
import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.model.TaskList;
import com.secondstudio.letsrun.utility.WebUtils;

public class TaskItemView extends MyDialog {

    private int position;
    private Context mContext;
    public TaskItemView(Context context, int p) {
        super(context);
        mContext = context;
        setContentView(R.layout.view_task_item);
        position = p;
        setReady();
    }

    protected void setReady(){
        TextView title = findViewById(R.id.textView_taskName);
        TextView detail = findViewById(R.id.detail);
        title.setText(TaskList.getTasks().get(position).getTitle());
        detail.setText(TaskList.getTasks().get(position).getDetail());
        final Button button = findViewById(R.id.btn);
        if (TaskList.getTasks().get(position).getDone() == 1){  //已完成
            button.setText("已完成");
            button.setEnabled(false);
        }else {
            if (TaskList.getTasks().get(position).getTitle()
                    .equals(mActivity.getResources().getStringArray(R.array.taskTitles)[0])) {
                //如果打开的任务是晨跑
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int result = WebUtils.getInfoNoPara("runSign");
                        if (result != 1) {
                            button.setVisibility(View.INVISIBLE);   //隐藏签到按钮
                        } else {
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            int r = WebUtils.doTask("runDone");
                                            if (r == 1) {
                                                TaskList.getTasks().get(position).setDone(1);   //标明已做
                                                Player.Load_MV(mContext.getSharedPreferences("player_info", Context.MODE_PRIVATE)
                                                        .getString("player_account", ""));    //加载玩家信息同时刷新主界面
                                                mActivity.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        button.setText("已完成");
                                                    }
                                                });
                                                button.setEnabled(false);
                                            } else {
                                                Looper.prepare();
                                                Toast.makeText(mActivity, "签到失败", Toast.LENGTH_SHORT).show();
                                                Looper.loop();
                                            }
                                        }
                                    }).start();
                                }
                            });
                        }
                    }
                }).start();
            } else {
                button.setText("开始任务");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WebTaskView webTaskView = new WebTaskView(mActivity, position);
                        webTaskView.show();
                        dismiss();
                    }
                });
            }
        }
        LinearLayout close = findViewById(R.id.button_back);    //关闭按钮
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
