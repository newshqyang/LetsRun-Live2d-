package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.model.TaskList;

public class TaskListLayout extends LinearLayout {

    public ListView mListViewTasks;
    public TaskListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局
        inflate(context, R.layout.task_layout, this);
        //获取控件
        mListViewTasks = (ListView)findViewById(R.id.lv_task);
    }

    //刷新任务列表的内容
    public void refreshList() {
        mListViewTasks.setAdapter(new TaskListLayoutAdapter());
    }

    /**
     * 自定义任务列表适配器
     */
    class TaskListLayoutAdapter extends BaseAdapter {
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
            View view = View.inflate(getContext(), R.layout.task_item,null);
            ImageView taskIcon = (ImageView)view.findViewById(R.id.imageView_completeTask);
            TextView taskName = (TextView)view.findViewById(R.id.textView_taskName);
            if (TaskList.getTasks().get(position).getDone() == 1){
                taskIcon.setImageDrawable(getResources().getDrawable(R.drawable.done));
            }
            taskName.setText(TaskList.getTasks().get(position).getTitle());
            return view;
        }
    }
}
