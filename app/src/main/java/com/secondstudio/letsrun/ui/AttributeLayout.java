package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.model.Player;
import com.secondstudio.letsrun.model.TaskList;


public class AttributeLayout extends LinearLayout implements View.OnClickListener {
    private TextView mName;
    private TextView mLevel;
    private ProgressBar mHp;
    private ImageView mMedal;
    private MedalView mMedalView;

    public AttributeLayout(Context context) {
        super(context);
    }

    public AttributeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.attribute_layout,this);
        //获取控件
        mName = findViewById(R.id.name);
        mLevel = findViewById(R.id.level);
        mHp = findViewById(R.id.sb_hp);
        mMedal = findViewById(R.id.button_medal);
        mMedal.setOnClickListener(this);
        mMedalView = new MedalView(context);
    }

    /**
     * 将信息呈现在属性面板上
     */
    public void Load(){
        mName.setText(Player.getName());
        mLevel.setText("Lv " + Player.getLevel());
        if (Player.getLevel() >= 7) {
            mMedal.setImageDrawable(getContext().getResources().getDrawable(R.drawable.medal_icon_get));
            mMedalView.setGetText();
        }
        refreshHPandExp();
    }

    public void refreshHPandExp() {
        mHp.setProgress(5 - TaskList.GetNotDoneSize());
    }

    public AttributeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_medal) {
            mMedalView.show();
        }
    }
}
