package com.secondstudio.letsrun.uselessCode;

import android.app.Activity;
import android.widget.Toast;

import com.secondstudio.letsrun.R;

public class GameUtil {

    //关卡标志
    public static String CP_BASICS = "basics";
    //关卡内的，建筑标志
    public static String BUILDING_WeaponStore  = "weaponStore";

    //双击返回键退出
    public static long startTime = 0;
    public static void doubleExit(Activity activity) {
        if ((System.currentTimeMillis() - startTime) > 2000) {
            Toast.makeText(activity, activity.getResources().getString(R.string.exit_game), Toast.LENGTH_SHORT).show();
            startTime = System.currentTimeMillis();
        } else {
            activity.finish();
        }
    }
}
