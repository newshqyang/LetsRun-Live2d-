package com.secondstudio.letsrun.uselessCode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class StringUtil {

    public static String GAME_DATA = "GAME_DATA";   //缓存文件名

    //获取单位的升级所需经验值
    public static int getUpgradeNowExp(int exp) {
        int upgrade_exp = 10;       //基础升级经验值
        int tmp_lv = 1;     //最低等级
        while (exp >= 10) {
            exp -= upgrade_exp;
            tmp_lv++;
            upgrade_exp += tmp_lv * 10;
        }
        return exp;
    }

    //获取单位的升级所需经验值
    public static int getUpgradeNeedExp(int exp) {
        int upgrade_exp = 10;       //基础升级经验值
        int tmp_lv = 1;     //最低等级
        while (exp >= 10) {
            exp -= upgrade_exp;
            tmp_lv++;
            upgrade_exp += tmp_lv * 10;
        }
        return upgrade_exp;
    }

    //获取单位的等级
    public static int getLv(int exp) {
        int upgrade_exp = 10;       //基础升级经验值
        int tmp_lv = 1;     //最低等级
        while (exp >= 10) {
            exp -= upgrade_exp;
            tmp_lv++;
            upgrade_exp += tmp_lv * 10;
        }
        return tmp_lv;
    }

    //获取缓存中的游戏数据
    public static JSONObject getGameData(Context context) throws JSONException {
        SharedPreferences preferences = context.getSharedPreferences(GAME_DATA, Context.MODE_PRIVATE);
        return new JSONObject(preferences.getString(GAME_DATA, "{\"status\" : \"failed\"}"));
    }

    //缓存游戏数据
    public static void saveGameData(Context context, JSONObject data) {
        SharedPreferences preferences = context.getSharedPreferences(GAME_DATA, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GAME_DATA, data.toString());
        editor.apply();
    }

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
