package com.chen.smartapplication.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.smartapplication.R;
import com.chen.smartapplication.entity.MyUser;
import com.chen.smartapplication.ui.LoginActivity;
import com.chen.smartapplication.view.CustomDialog;

import java.io.File;
import java.security.PermissionCollection;
import java.security.acl.Permission;
import java.util.jar.Manifest;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen_ on 2017-01-16.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    private static final int CAMERA_REQUEST_CODE = 100;
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    private Button btn_update_ok;

    private CustomDialog dialog;
    private CircleImageView profile_image;

    private Button btn_camera;
    private Button btn_cancel;
    private Button btn_picture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        dialog = new CustomDialog(getActivity(), 100, 100, R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);
//        dialog.setCancelable(false);

        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);


        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);
        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_exit_user.setOnClickListener(this);
        edit_user.setOnClickListener(this);
        btn_update_ok.setOnClickListener(this);

        //默认有值，不可编辑
        setEnabled(false);

        //设置具体值
        MyUser currentUser = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(currentUser.getUsername());
        et_age.setText(currentUser.getAge() + "");
        et_sex.setText(currentUser.isSex() ? "男" : "女");
        et_desc.setText(currentUser.getDesc());
    }

    public void setEnabled(boolean is) {
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
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

                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                String username = et_username.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();

                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
                    MyUser myUser = new MyUser();
                    myUser.setUsername(username);
                    myUser.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        myUser.setSex(true);
                    } else {
                        myUser.setSex(false);
                    }
                    if (TextUtils.isEmpty(desc)) {
                        desc = "这个人很懒，什么都没有留下";
                    }
                    myUser.setDesc(desc);

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    myUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "个人信息更新成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "个人信息更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
        }
    }

    private void toCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    private void toPicture() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:

                break;

        }
    }
}
