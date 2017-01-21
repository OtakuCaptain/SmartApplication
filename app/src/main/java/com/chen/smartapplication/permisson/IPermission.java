package com.chen.smartapplication.permisson;

import java.util.List;


public interface IPermission {

    void onGranted();

    void onDenied(List<String> deniedPermission);
}
