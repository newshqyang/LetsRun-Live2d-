package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.model.Player;
import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.model.TaskFactory;
import com.secondstudio.letsrun.model.TaskList;
import com.secondstudio.letsrun.utility.WebUtils;

public class WebTaskView extends MyDialog implements View.OnClickListener {
    private int mPosition;

    public WebTaskView(Context context, int position) {
        super(context);
        mPosition = position;
        setContentView(R.layout.web_task_view);
        setReady();
    }

    //根据任务给出网址
    private void getTaskUrl(int position) {
        String[] taskWebUrl = mActivity.getResources().getStringArray(R.array.taskWebUrl);
        switch (position){
            case 1:
                time = TaskFactory.LAW_TIME;
                code = TaskFactory.LAW_DONE;
                webUrl = taskWebUrl[0];
                break;
            case 2:
                time = TaskFactory.POEM_TIME;
                code = TaskFactory.POEM_DONE;
                webUrl = taskWebUrl[1];
                break;
            case 3:
                time = TaskFactory.HISTORY_TIME;
                code = TaskFactory.HISTORY_DONE;
                webUrl = taskWebUrl[2];
                break;
            case 4:
                time = TaskFactory.NEWS_TIME;
                code = TaskFactory.NEWS_DONE;
                webUrl = taskWebUrl[3];
                break;
        }
    }

    private int time = 30;

    private String code = TaskFactory.LAW_DONE;
    public String webUrl;
    private WebView web;
    private TextView mTitle;
    private TextView mTime;
    private LinearLayout mClose;
    private LinearLayout mHomePage;
    private LinearLayout mPrevPage;
    private LinearLayout mNextPage;
    private TimeThread timeThread = null;
    @Override
    protected void setReady() {
        getTaskUrl(mPosition);  //获取网址,并初始化时间

        web = findViewById(R.id.webView);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);//设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.setHorizontalScrollBarEnabled(false);
        web.setVerticalScrollbarOverlay(true);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient());

        mTitle = findViewById(R.id.title);
        mTitle.setText(TaskList.getTasks().get(mPosition).getTitle());
        mTime = findViewById(R.id.time);
        mTime.setText(convertTime(time));
        mClose = findViewById(R.id.close);
        mClose.setOnClickListener(this);
        mHomePage = findViewById(R.id.homePage);
        mHomePage.setOnClickListener(this);
        mPrevPage = findViewById(R.id.prevPage);
        mPrevPage.setOnClickListener(this);
        mNextPage = findViewById(R.id.nextPage);
        mNextPage.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        mActivity.state_quiet = 1;
        web.loadUrl(webUrl);
        if (TaskList.getTasks().get(mPosition).getDone() == 0) {
            timeThread = new TimeThread(time);
            timeThread.start();
        }
    }

    //转化时间为00:01
    private String convertTime(int time) {
        int minute = time / 60;
        int second = time % 60;
        StringBuilder builder = new StringBuilder();
        if (minute < 10) {
            builder.append(0);
        }
        builder.append(minute).append(":");
        if (second < 10) {
            builder.append(0);
        }
        builder.append(second);
        return builder.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.homePage:
                web.loadUrl(webUrl);
                break;
            case R.id.prevPage:
                if (web.canGoBack()) {
                    web.goBack();
                }
                break;
            case R.id.nextPage:
                if (web.canGoForward()) {
                    web.goForward();
                }
                break;
        }
    }


    private int state = 0;
    class TimeThread extends Thread{
        private int tTime = TaskFactory.LAW_TIME;
        TimeThread(int time) {
            tTime = time;
        }
        @Override
        public void run() {
            while (tTime > 0) {
                if (state == -1) {
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tTime--;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTime.setText(convertTime(tTime));
                    }
                });
            }
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTime.setText("已完成");
                }
            });
            int r = WebUtils.doTask(code);
            if (r != 1) {
                Toast.makeText(mActivity, "Error: 1009", Toast.LENGTH_SHORT).show();
            } else {
                Player.Load_MV(Player.mAccount);    //加载玩家信息同时刷新主界面
            }
        }
    }

    @Override
    public void dismiss() {
        state = -1;
        mActivity.state_quiet = 0;
        super.dismiss();
    }
}
