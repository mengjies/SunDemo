package com.sunyard.sundemo.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.activity.MainActivity;
import com.sunyard.sundemo.common.base.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by MengJie on 2017/8/25.
 */

public class OperateSuccessFragment extends BaseFragment {
    public static final String TAG = "OperateSuccessFragment";
    @InjectView(R.id.tv_msg)
    TextView tvMsg;
    @InjectView(R.id.tv_num)
    TextView tvNum;
    @InjectView(R.id.tv_to_main)
    TextView tvToMain;
    private Timer countDownTimer;

    private int remainSecond = 3;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            tvNum.setText(String.valueOf(remainSecond));
            remainSecond--;
            if (remainSecond < 0) {
                countDownTimer.cancel();// 取消
                //跳转到首页
                startActivity(MainActivity.class, true);
            }
        }
    };

    public static OperateSuccessFragment newInstance() {

        Bundle args = new Bundle();

        OperateSuccessFragment fragment = new OperateSuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operate_success, container, false);
        ButterKnife.inject(this, view);

        //倒计时
        startCountDown();

        return view;
    }

    private void startCountDown() {
        countDownTimer = new Timer();
        countDownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(new Message());
            }
        }, 0, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);

        countDownTimer.cancel();
        handler.removeCallbacksAndMessages(null);
    }

    @OnClick(R.id.tv_to_main)
    public void onClick() {
        //跳转到首页
        startActivity(MainActivity.class, true);
    }
}
