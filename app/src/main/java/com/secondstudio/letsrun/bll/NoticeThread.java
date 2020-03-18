package com.secondstudio.letsrun.bll;

import android.view.View;

import com.live2d.yx.LAppDefine;
import com.secondstudio.letsrun.GameActivity;
import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.model.TaskList;
import com.secondstudio.letsrun.ui.WebTaskView;
import com.secondstudio.letsrun.utility.StringUtil;

public class NoticeThread extends Thread {
    private GameActivity mActivity;
    public NoticeThread(GameActivity activity) {
        mActivity = activity;
        mActivity.state = 1;
    }

    private static final int SLEEP_TIME = 7000; //提醒的等待时间
    @Override
    public void run() {
        int sum = 0;
        while (mActivity.state == 1) {
            if (sum % 10 == 0) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int size = TaskList.GetNotDoneSize();
                        if (size == 0) {
                            StringUtil.setText(mActivity, mActivity.mNotice, "很好哦~今天的任务都完成了呢~");
                        } else {
                            StringUtil.setText(mActivity, mActivity.mNotice, "今天还有" + size + "个任务没有完成哦！");
                        }
                        if (mActivity.state_quiet == 0) {
                            SwitchTaskSound(size);
                        }
                        mActivity.mNotice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mActivity.mNotice.setVisibility(View.GONE);
                            }
                        });
                        mActivity.mNotice.setVisibility(View.VISIBLE);
                    }
                    //发出多少个任务没有完成的声音
                    private void SwitchTaskSound(int size) {
                        switch (size) {
                            case 0:
                                mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                        LAppDefine.PRIORITY_NORMAL, 5);//全部完成
                                break;
                            case 1:
                                mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                        LAppDefine.PRIORITY_NORMAL, 0);//1个
                                break;
                            case 2:
                                mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                        LAppDefine.PRIORITY_NORMAL, 1); //2个
                                break;
                            case 3:
                                mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                        LAppDefine.PRIORITY_NORMAL, 2); //3个
                                break;
                            case 4:
                                mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                        LAppDefine.PRIORITY_NORMAL, 3); //4个
                                break;
                            case 5:
                                mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                        LAppDefine.PRIORITY_NORMAL, 4); //5个
                                break;
                        }
                    }
                });
            } else {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int random = (int)(Math.random() * 2);
                        switch (random) {
                            case 0:
                                PoemOperation();
                                if (mActivity.state_quiet == 0) {
                                    mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                            LAppDefine.PRIORITY_NORMAL, 7);
                                }
                                break;
                            case 1:
                                LawOperation();
                                if (mActivity.state_quiet == 0) {
                                    mActivity.live2DMgr.mModel.startMotionByNo(LAppDefine.MOTION_GROUP_TAP_BODY,
                                            LAppDefine.PRIORITY_NORMAL, 6);
                                }
                        }
                    }

                    private void LawOperation() {
                        String[] lawWords = mActivity.getResources().getStringArray(R.array.lawWords);
                        final int r = (int)(Math.random() * lawWords.length);
                        mActivity.mNotice.setText("");
                        StringUtil.setText(mActivity, mActivity.mNotice, lawWords[r]);
                        mActivity.mNotice.setVisibility(View.VISIBLE);
                        mActivity.mNotice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mActivity.mNotice.setVisibility(View.GONE);
                                String[] lawUrl = mActivity.getResources().getStringArray(R.array.lawUrl);
                                WebTaskView webTaskView = new WebTaskView(mActivity, 1);
                                webTaskView.webUrl = lawUrl[r];
                                webTaskView.show();
                            }
                        });
                    }

                    //诗歌相关
                    private void PoemOperation() {
                        String[] poemWords = mActivity.getResources().getStringArray(R.array.poemWords);
                        final int r = (int)(Math.random() * poemWords.length);
                        StringUtil.setText(mActivity, mActivity.mNotice, poemWords[r]);
                        mActivity.mNotice.setVisibility(View.VISIBLE);
                        mActivity.mNotice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mActivity.mNotice.setVisibility(View.GONE);
                                String[] poemUrl = mActivity.getResources().getStringArray(R.array.poemUrl);
                                WebTaskView webTaskView = new WebTaskView(mActivity, 2);
                                webTaskView.webUrl = poemUrl[r];
                                webTaskView.show();
                            }
                        });
                    }
                });
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mActivity.mNotice.setVisibility(View.GONE);
                }
            });
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum++;
        }
    }
}
