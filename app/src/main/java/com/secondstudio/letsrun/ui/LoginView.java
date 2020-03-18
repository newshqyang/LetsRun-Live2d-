package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.bll.NoticeThread;
import com.secondstudio.letsrun.model.Player;
import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.bll.Music;
import com.secondstudio.letsrun.utility.WebUtils;

public class LoginView extends MyDialog implements View.OnClickListener {


    public LoginView(Context context) {
        super(context);
        setContentView(R.layout.view_login);
        mActivity.state_quiet = 1;
        setReady();
    }

    private EditText mAccount;
    private EditText mPwd;
    private ImageButton mLogin;
    private TextView mRegister;
    private TextView mForgetPwd;
    private TextView mExit;
    @Override
    protected void setReady() {
        mAccount = findViewById(R.id.et_acc);
        mPwd = findViewById(R.id.et_pwd);
        mLogin = findViewById(R.id.btn_login);
        mRegister = findViewById(R.id.reg);
        mRegister.setOnClickListener(this);
        mForgetPwd = findViewById(R.id.forget_pwd);
        mForgetPwd.setOnClickListener(this);
        mExit = findViewById(R.id.exit);
        mExit.setOnClickListener(this);

        mLogin.setOnClickListener(this);

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.mGameView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.exit:
                LoginView.this.dismiss();
                mActivity.finish();
                break;
            case R.id.reg:
                LoginView.this.dismiss();
                RegisterView registerView = new RegisterView(getContext());
                registerView.setCancelable(false);
                registerView.show();
                break;
            case R.id.forget_pwd:
                Toast.makeText(getContext(), "联系管理员：15300572017", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //登录操作
    private void login() {
        if (mAccount.length() < 1 || mPwd.length() < 1) {
            Toast.makeText(getContext(), "请输入账号、密码", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int result = WebUtils.login(mAccount.getText().toString(), mPwd.getText().toString());
                Looper.prepare();
                if (result == 1) {
                    Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getContext().getSharedPreferences("player_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLogun", true);
                    editor.putString("player_account", mAccount.getText().toString());
                    editor.apply();
                    Music.prepare();
                    Music.playMusic();  //播放音乐
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.mGameView.setVisibility(View.VISIBLE);
                        }
                    });
                    Player.mAccount = mAccount.getText().toString();
                    Player.Load_MV(mAccount.getText().toString());    //加载玩家信息同时刷新主界面
                    LoginView.this.dismiss();
                    mActivity.mNoticeThread = new NoticeThread(mActivity);
                    mActivity.mNoticeThread.start();    //启动提醒功能
                    mActivity.state_quiet = 0;
                    Looper.loop();
                } else if (result == 0) {
                    Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
                } else if (result == -1) {
                    Toast.makeText(getContext(), "账号未注册", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error: 1006", Toast.LENGTH_SHORT).show();
                }
                Looper.loop();
            }
        }).start();
    }
}
