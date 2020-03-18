package com.secondstudio.letsrun.utility;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StringUtil {

    //逐字加载方法
    public static void setText(final AppCompatActivity activity, final TextView textView, final String text) {
        final int len = text.length();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                StringBuilder tmp = new StringBuilder();
                do {
                    tmp.append(text.charAt(i));
                    final String finalTmp = tmp.toString();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(finalTmp);
                        }
                    });
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                } while (i < len);
            }
        }).start();
    }
}
