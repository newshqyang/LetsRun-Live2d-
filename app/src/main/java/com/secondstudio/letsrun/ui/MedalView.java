package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.R;

public class MedalView extends MyDialog {
    public MedalView(Context context) {
        super(context);
        setContentView(R.layout.view_medal);
        setReady();
    }

    private TextView mTvMedal;
    public void setGetText() {
        mTvMedal.setText("“达人”勋章，已获得。");
    }
    @Override
    protected void setReady() {
        mTvMedal = findViewById(R.id.tv_medal);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
}
