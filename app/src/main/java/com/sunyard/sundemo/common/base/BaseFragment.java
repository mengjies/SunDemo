package com.sunyard.sundemo.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.Map;

/**
 * BaseFragment
 */

public class BaseFragment extends Fragment {

    public Activity act;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
    }

    public void startActivity(Class clazz) {
        Intent intent = new Intent(act, clazz);
        act.startActivity(intent);
    }

    public void startActivity(Class clazz, boolean isFinish) {
        Intent intent = new Intent(act, clazz);
        act.startActivity(intent);
        if (isFinish) {
            act.finish();
        }
    }

    public void startActivity(Class clazz, Map<String, String> map) {
        Intent intent = new Intent(act, clazz);
        if (map != null) {
            for (String key : map.keySet()) {
                intent.putExtra(key, map.get(key));
            }
        }

        act.startActivity(intent);
    }
}
