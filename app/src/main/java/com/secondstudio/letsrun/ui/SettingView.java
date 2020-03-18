package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.bll.Music;

public class SettingView extends MyDialog implements View.OnClickListener {
    public SettingView(Context context) {
        super(context);
        setContentView(R.layout.view_setting);
        setReady();
    }

    private TextView mTextViewBGMName;
    private ImageButton mImageButtonPlay;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrev;

    private static AudioManager am;    //此方法用来设置音量，am是系统音量管理器声明的对象
    protected void setReady(){

        LinearLayout mBack = findViewById(R.id.button_back);
        mBack.setOnClickListener(this);
        LinearLayout mUnregister = findViewById(R.id.button_unregister);
        mUnregister.setOnClickListener(this);
        LinearLayout mExit = findViewById(R.id.button_exit);
        mExit.setOnClickListener(this);

        //设置MP3播放器
        mTextViewBGMName = findViewById(R.id.textViewBGMName);
        mTextViewBGMName.setText(Music.getMusicName());

        mImageButtonPlay = findViewById(R.id.imageButtonPlayBGM);
        if (Music.isPlaying()){
            mImageButtonPlay.setImageDrawable(getContext().getResources().getDrawable(R.drawable.pause));
        }
        mImageButtonPlay.setOnClickListener(this);

        mImageButtonNext = findViewById(R.id.imageButtonNextBGM);
        mImageButtonPrev = findViewById(R.id.imageButtonPrevBGM);
        //监听玩家点击上一首播放
        if (Music.getRandomNum()==1){
            //如果此时是第一首BGM，就隐藏前一首图标
            mImageButtonPrev.setVisibility(View.INVISIBLE);
        }
        mImageButtonPrev.setOnClickListener(this);
        //监听玩家点击下一首播放
        if (Music.getRandomNum()==Music.getMusicSize()){
            //如果此时是最后一首BGM，就隐藏下一首BGM图标
            mImageButtonNext.setVisibility(View.INVISIBLE);
        }
        mImageButtonNext.setOnClickListener(this);

        //设置BGM音量
        SeekBar seekBarBGM = findViewById(R.id.seekBarBGM);
        seekBarBGM.setProgress((int)(Music.getVolume()*100));
        seekBarBGM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    seekBar.setProgress(i);
                    //设置游戏背景音量
                    float currentBGMVolume = (float)i/100;
                    //缓存音量设置
                    Music.setVolume(currentBGMVolume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //设置游戏音量
        SeekBar seekBarGameVolume = findViewById(R.id.seekBarGameVolume);
        am = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);   //获取系统最大音量
        seekBarGameVolume.setMax(maxVolume);
        int currentGameVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);  //获取当前音量
        seekBarGameVolume.setProgress(currentGameVolume);
        seekBarGameVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    //设置音量
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                    int currentGameVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                    seekBar.setProgress(currentGameVolume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_unregister:
                unregister();
                break;
            case R.id.button_exit:
                SettingView.this.dismiss();
                mActivity.finish();
                break;
            case R.id.button_back:
                dismiss();
                break;
            case R.id.imageButtonPrevBGM:
                Music.prev();
                mTextViewBGMName.setText(Music.getMusicName());  //刷新BGM,刷新BGMName
                mImageButtonPlay.setImageDrawable(getContext().getResources().getDrawable(R.drawable.pause));//放上暂停图标
                mImageButtonNext.setVisibility(View.VISIBLE);
                if (Music.getRandomNum()==1){
                    //如果此时是第一首BGM，就隐藏前一首图标
                    mImageButtonPrev.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.imageButtonNextBGM:
                Music.next();
                mTextViewBGMName.setText(Music.getMusicName());  //刷新BGM,刷新BGMName
                mImageButtonPlay.setImageDrawable(getContext().getResources().getDrawable(R.drawable.pause));//显示暂停图标
                mImageButtonPrev.setVisibility(View.VISIBLE);
                if (Music.getRandomNum()==Music.getMusicSize()){
                    //如果此时是最后一首BGM，就隐藏下一首BGM图标
                    mImageButtonNext.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.imageButtonPlayBGM:       //玩家点击播放键时
                if (Music.isPlaying()){  //如果MP正在播放，就暂停
                    Music.pause();  //暂停MP
                    mImageButtonPlay.setImageDrawable(getContext().getResources().getDrawable(R.drawable.play));//显示播放图标
                }else {
                    Music.start();   //开始MP
                    mImageButtonPlay.setImageDrawable(getContext().getResources().getDrawable(R.drawable.pause));//显示暂停图标
                }
                break;
        }
    }

    //退出当前账号
    private void unregister() {
        Music.stop();
        SharedPreferences preferences = getContext().getSharedPreferences("player_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogun", false);
        editor.apply();
        SettingView.this.dismiss();
        LoginView loginView = new LoginView(getContext());
        loginView.show();
        mActivity.state = 0;
    }
}
