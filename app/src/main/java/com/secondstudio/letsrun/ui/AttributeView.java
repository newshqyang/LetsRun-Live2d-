package com.secondstudio.letsrun.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.secondstudio.letsrun.model.Player;
import com.secondstudio.letsrun.model.TaskList;
import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.R;

public class AttributeView extends MyDialog {

    private Context mContext;
    public AttributeView(Context context) {
        super(context);
        mContext = context;
        setContentView(R.layout.view_player_info);
        setReady();
    }

    /**
     * 主方法
     */
    @SuppressLint("SetTextI18n")
    protected void setReady() {
        //加载布局
        LinearLayout mButtonClose = findViewById(R.id.button_close);
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //头像后期再可自定义
//        ImageView headImage = findViewById(R.id.player_headImage);

        TextView playerName = findViewById(R.id.textView_playerName);
        playerName.setText(Player.getName());           //姓名
        TextView mLv = findViewById(R.id.textView_lv);
        mLv.setText("Lv " + Player.getLevel());        //等第
        TextView mExp = findViewById(R.id.textView_exp);
        mExp.setText((5 - TaskList.GetNotDoneSize())+"/5");    //当前Exp值比最大Exp值
        ProgressBar expNow =  findViewById(R.id.seekBar_exp);
        expNow.setMax(5);
        expNow.setProgress(5 - TaskList.GetNotDoneSize());

        MyListView listView =  findViewById(R.id.listView_taskInfo);
        listView.setAdapter(new TaskAdapter());
    }


    class TaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return TaskList.getTasks().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext, R.layout.view_task_size,null);
            TextView name = (TextView)view.findViewById(R.id.textView_taskName);
            name.setText(TaskList.getTasks().get(position).getTitle());
            TextView size = (TextView)view.findViewById(R.id.textView_taskSize);
            size.setText(TaskList.getTasks().get(position).getDoneSize()+"");
            return view;
        }
    }

}
