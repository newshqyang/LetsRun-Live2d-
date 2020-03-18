package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.secondstudio.letsrun.model.MyDialog;
import com.secondstudio.letsrun.bll.NoticeThread;
import com.secondstudio.letsrun.model.Player;
import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.bll.Music;
import com.secondstudio.letsrun.utility.WebUtils;

public class RegisterView extends MyDialog implements View.OnClickListener {
    public RegisterView(Context context) {
        super(context);
        setContentView(R.layout.view_register);
        mActivity.state_quiet = 0;
        setReady();
    }

    private EditText mAccount;
    private EditText mPwd;
    private EditText mPwdConfirm;
    private EditText mName;
    private Button mRegister;
    private Button mBack;
    @Override
    protected void setReady() {
        mAccount = findViewById(R.id.et_acc);
        mPwd = findViewById(R.id.et_pwd);
        mPwdConfirm = findViewById(R.id.et_pwd_confirm);
        mName = findViewById(R.id.et_name);
        mRegister = findViewById(R.id.btn_register);
        mRegister.setOnClickListener(this);
        mBack = findViewById(R.id.backToLogin);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backToLogin:
                RegisterView.this.dismiss();
                LoginView loginView = new LoginView(getContext());
                loginView.show();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    //注册相关
    private void register() {
        if (mAccount.length() > 15 || mPwd.length() > 50 || mPwdConfirm.length() > 50) {
            Toast.makeText(getContext(), "超出规定字符数！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mAccount.length() < 1 || mPwdConfirm.length() < 1 || mPwd.length() < 1) {
            Toast.makeText(getContext(), "请填写完整", Toast.LENGTH_SHORT).show();
        } else if (!mPwdConfirm.getText().toString().equals(mPwd.getText().toString())) {
            Toast.makeText(getContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int r = WebUtils.register(mAccount.getText().toString(), mPwd.getText().toString(),
                            mName.getText().toString());
                    Looper.prepare();
                    if (r == 1) {
                        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getContext().getSharedPreferences("player_info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isLogun", true);
                        editor.putString("player_account", mAccount.getText().toString());
                        editor.apply();
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivity.mGameView.setVisibility(View.VISIBLE); //显示主界面内容
                            }
                        });
                        Player.mAccount = mAccount.getText().toString();
                        Player.Load_MV(mAccount.getText().toString());    //加载玩家信息同时刷新主界面
                        RegisterView.this.dismiss();
                        Music.prepare();
                        Music.playMusic();
                        mActivity.mNoticeThread = new NoticeThread(mActivity);
                        mActivity.mNoticeThread.start();    //启动提醒功能
                        mActivity.state_quiet = 0;
                    } else if (r == -1){
                        Toast.makeText(getContext(), "账号已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error: 1007", Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
            }).start();
        }
    }
}
