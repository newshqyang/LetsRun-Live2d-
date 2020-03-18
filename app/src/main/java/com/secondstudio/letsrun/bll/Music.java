package com.secondstudio.letsrun.bll;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;

import com.secondstudio.letsrun.GameActivity;
import com.secondstudio.letsrun.R;

import java.io.IOException;

public class Music {

    public static int state = 0;
    static private GameActivity mGameActivity;  //设置Music的静态数据GameActivity
    public static void setGameActivity(GameActivity gameActivity) {
        mGameActivity = gameActivity;
    }

    private static int mMusicSize;     //音乐总数
    public static void setMusicSize(int musicSize) {
        mMusicSize = musicSize;
    }
    public static int getMusicSize(){//获取音乐总数
        return mMusicSize;
    }

    private static int mRandomNum;
    static public void makeRandomNum(){ //产生随机数
        mRandomNum = (int)Math.random() * mMusicSize;
        if (mRandomNum == 0){
            mRandomNum = 1;
        }
    }
    public static int getRandomNum(){//获取当前随机数
        return mRandomNum;
    }

    private static String mMusicName;
    private static String mPath;
    private int mRandomMusic;


    public static String getMusicName() {  //获取音乐名
        return mMusicName;
    }

    public static String getPath() {    //获取音乐路径
        mPath = "android.resource://"+mGameActivity.getPackageName()+"/";
        switch (mRandomNum){
            case 1:
                mMusicName = mGameActivity.getResources().getString(R.string.bgm_0);//设定音乐名
                mPath += R.raw.bgm_0;
                break;
            case 2:
                mMusicName = mGameActivity.getResources().getString(R.string.bgm_1);
                mPath += R.raw.bgm_1;
                break;
            case 3:
                mMusicName = mGameActivity.getResources().getString(R.string.bgm_2);
                mPath += R.raw.bgm_2;
                break;
            default:
                mMusicName = "未知音乐";
        }
        return mPath;
    }

    public int getRandomMusic() {
        return mRandomMusic;
    }

    //获取缓存的音量
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static float currentMusicVolume;
    public static MediaPlayer mMediaPlayer;

    /**
     * 播放音乐
     */
    public static boolean isPlaying(){  //判断是否正在播放
        if (mMediaPlayer.isPlaying()){
            return true;
        }else {
            return false;
        }
    }

    public static void start(){//播放
        mMediaPlayer.start();
    }
    public static void pause(){ //暂停
        mMediaPlayer.pause();
    }
    public static void stop(){  //停止
        mMediaPlayer.stop();
        mMediaPlayer.release();
        state = 0;
    }
    public static void setPrevNumber(){  //获取上一个可用序号
        mRandomNum--;
        if (mRandomNum <= 0){
            mRandomNum = 1;
        }
    }
    public static void prev(){  //播放上一首音乐
        setPrevNumber();
        playMusic();
    }
    public static void setNextNumber(){  //获取下一个可用序号
        mRandomNum++;
        if (mRandomNum > mMusicSize){
            mRandomNum %= mMusicSize;
        }
    }
    public static void next(){  //播放下一首音乐
        setNextNumber();
        playMusic();
    }

    static public void prepare(){//Music初始化
        mMediaPlayer = new MediaPlayer();
        mSharedPreferences =
                mGameActivity.getSharedPreferences("player_info", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        currentMusicVolume = mSharedPreferences.getFloat("MusicVolume",0.5f);
        mMediaPlayer.setVolume(currentMusicVolume,currentMusicVolume);
        makeRandomNum();
    }
    
    static public float getVolume(){    //获取当前音量
        return currentMusicVolume;
    }
    
    static public void setVolume(float volume){//设置音量
        currentMusicVolume = volume;
        mMediaPlayer.setVolume(currentMusicVolume,currentMusicVolume);
        mEditor.putFloat("MusicVolume",currentMusicVolume);
        mEditor.commit();
    }
    public static void playMusic(){ //播放音乐主方法
        mMediaPlayer.reset();
        try {
            //获取内部一首音乐资源
            mMediaPlayer.setDataSource(mGameActivity, Uri.parse(Music.getPath()));
            //MediaPlayer准备就绪
            mMediaPlayer.prepare();
            //设置这首音乐资源播放时的音量

        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置这首音乐循环播放
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //当前音乐结束时，播放下一首
                next();
            }
        });
        //开始播放
        mMediaPlayer.start();
        state = 1;
    }
}
