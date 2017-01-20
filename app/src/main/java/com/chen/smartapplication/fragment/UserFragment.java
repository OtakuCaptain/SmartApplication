package com.chen.smartapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chen.smartapplication.R;
import com.chen.smartapplication.entity.MyUser;
import com.chen.smartapplication.ui.LoginActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by chen_ on 2017-01-16.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);
        btn_exit_user.setOnClickListener(this);
        edit_user.setOnClickListener(this);

        //默认有值，不可编辑
        et_username.setEnabled(false);
        et_sex.setEnabled(false);
        et_age.setEnabled(false);
        et_desc.setEnabled(false);

        //设置具体值
        MyUser currentUser = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(currentUser.getUsername());
        et_age.setText(currentUser.getAge() + "");
        et_sex.setText(currentUser.isSex() ? "男" : "女");
        et_desc.setText(currentUser.getDesc());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:
                //退出登录
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = MyUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_user:
                //编辑资料

                break;
        }
    }
}
