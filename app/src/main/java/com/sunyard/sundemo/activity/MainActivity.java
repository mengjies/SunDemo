package com.sunyard.sundemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.common.base.ActivityUtils;
import com.sunyard.sundemo.common.base.BaseActivity;
import com.sunyard.sundemo.common.utils.ToastUtils;
import com.sunyard.sundemo.model.SunRepository;
import com.sunyard.sundemo.presenter.MainPresenter;
import com.sunyard.sundemo.view.fragment.MainFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private ActionBar actionBar;
    private double firstClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //init title
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("商品");
        }

        //load fragment
        MainFragment mainFragment = MainFragment.newInstance();
        ActivityUtils.addFragmentToFrame(getSupportFragmentManager(), mainFragment, R.id.mainFrame, MainFragment.TAG);

        //create presenter
        new MainPresenter(mainFragment, new SunRepository());
    }

    //双击退出应用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 1000) {
                ToastUtils.showToast("再次点击退出");
                firstClick = secondClick;
                return true;
            } else {
                //退出应用
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
