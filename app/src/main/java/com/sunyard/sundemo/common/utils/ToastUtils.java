package com.sunyard.sundemo.common.utils;

import android.widget.Toast;

import com.sunyard.sundemo.MyApplication;


/**
 * Created by Administrator on 2016/3/23.
 * Toast管理
 */
public class ToastUtils {
    private static Toast toast = null;
    private static boolean isDebug = true;

    public static void showToast(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), msg, duration);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showTestToast(String msg) {
        if (isDebug) {
            showToast(msg);
        }
    }
}
