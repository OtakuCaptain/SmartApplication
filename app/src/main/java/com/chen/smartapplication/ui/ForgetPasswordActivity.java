package com.chen.smartapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chen.smartapplication.R;
import com.chen.smartapplication.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by chen on 2017-01-19.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_forget_password;
    private EditText et_email;
    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }

    private void initView() {
        et_email = (EditText) findViewById(R.id.et_email);
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget_password:
                final String email = et_email.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity
                                        .this, "已发送至邮箱：" + email, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgetPasswordActivity
                                        .this, "发送失败" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_password:
                String now = this.et_now.getText().toString().trim();
                String news = this.et_new.getText().toString().trim();
                String new_password = this.et_new_password.getText().toString().trim();
                if (!TextUtils.isEmpty(now) & !TextUtils.isEmpty(news) & !TextUtils.isEmpty(new_password)) {
                    //判断新密码是否一致
                    if (news.equals(new_password)) {
                        MyUser.updateCurrentUserPassword(now, news, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
