/**
 * 游戏的主活动页面，
 * 通过不断变换View，起到全屏作用。
 */
package com.secondstudio.letsrun;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.live2d.utils.android.FileManager;
import com.live2d.utils.android.SoundManager;
import com.live2d.yx.LAppDefine;
import com.live2d.yx.LAppLive2DManager;
import com.live2d.yx.LAppView;
import com.secondstudio.letsrun.ui.LoginView;
import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.bll.NoticeThread;
import com.secondstudio.letsrun.bll.Music;
import com.secondstudio.letsrun.ui.AttributeLayout;
import com.secondstudio.letsrun.model.Player;
import com.secondstudio.letsrun.model.TaskList;
import com.secondstudio.letsrun.ui.TaskListLayout;
import com.secondstudio.letsrun.utility.AndroidBug5497Workaround;
import com.secondstudio.letsrun.utility.ViewUtil;
import com.secondstudio.letsrun.ui.SettingView;
import com.secondstudio.letsrun.ui.TaskItemView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public int state = 1;
    public int state_quiet = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyDialog.setActivity(this);
        Player.setActivity(this);
        TaskList.setActivity(this);

        //音乐播放器部分
        Music.setGameActivity(this);//获取活动页面
        Music.setMusicSize(3);  //设置音乐数量

        setContentView(R.layout.view_main);
        AndroidBug5497Workaround.assistActivity(this);

        Load();//加载主视图的相关代码
    }

    /**
     * live2d相关
     */
    public LAppLive2DManager live2DMgr ;
    private void Load(){
        if(LAppDefine.DEBUG_LOG)
        {
            Log.d( "", "==============================================\n" ) ;
            Log.d( "", "   Live2D Sample  \n" ) ;
            Log.d( "", "==============================================\n" ) ;
        }
        FileManager.init(this);
        SoundManager.init(this);
        live2DMgr = new LAppLive2DManager();
        setupGUI();
    }

    /**
     * 主视图布局加载
     */
    public ConstraintLayout mGameView;
    public TaskListLayout mTaskListLayout;
    public AttributeLayout mAttributeLayout;
    public TextView mNotice;
    public NoticeThread mNoticeThread;
    private void setupGUI() {
        mGameView = findViewById(R.id.gameView);
        LAppView view = live2DMgr.createView(this);
        FrameLayout layout = findViewById(R.id.live2DLayout);
        layout.addView(view, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageButton iBtn = findViewById(R.id.imageButtonSetting);
        iBtn.setOnClickListener(this);

        //属性框
        mAttributeLayout = findViewById(R.id.attributeView);
        mAttributeLayout.setOnClickListener(this);
        mNotice = findViewById(R.id.notice);
        mNotice.setOnClickListener(this);

        //任务列表框
        mTaskListLayout = findViewById(R.id.taskList);
        mTaskListLayout.mListViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItemView taskItemView = new TaskItemView(GameActivity.this, position);
                taskItemView.show();
            }
        });

        SharedPreferences preferences = this.getSharedPreferences("player_info", MODE_PRIVATE);
        Player.mAccount = preferences.getString("player_account", "");
        boolean isLogun = preferences.getBoolean("isLogun", false);
        if (!isLogun) {
            mGameView.setVisibility(View.INVISIBLE);
            LoginView loginView = new LoginView(this);
            loginView.show();
            loginView.setCancelable(false);
        } else {
            Music.prepare();
            Music.playMusic();  //播放音乐
            Player.Load_MV(Player.mAccount);    //加载玩家信息同时刷新主界面
            mNoticeThread = new NoticeThread(this);
            mNoticeThread.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewUtil.fullScreen(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        live2DMgr.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        state = 0;
        SoundManager.release(); //live2d声音资源释放
        if (Music.state == 1) {
            Music.stop();   //BGM停止
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonSetting:
                SettingView settingView = new SettingView(this);
                settingView.show();
                break;
            case R.id.attributeView:
                //属性视图
                Player.Load_AvAndShow(Player.mAccount);
                break;
        }
    }
}
