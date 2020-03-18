package com.secondstudio.letsrun.utility;

import android.app.Activity;
import android.view.View;

public class ViewUtil {


    public static void fullScreen(final Activity activity){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //全屏
                View decorView = activity.getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        });
    }
}
