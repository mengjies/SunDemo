package com.sunyard.sundemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.common.base.ActivityUtils;
import com.sunyard.sundemo.common.base.BaseActivity;
import com.sunyard.sundemo.common.base.BaseFragment;
import com.sunyard.sundemo.view.fragment.OperateSuccessFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PaySuccessActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        ButterKnife.inject(this);

        //init title
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("付款成功");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //load fragment
        OperateSuccessFragment fragment = OperateSuccessFragment.newInstance();
        ActivityUtils.addFragmentToFrame(getSupportFragmentManager(), fragment, R.id.paySuccessFrame, OperateSuccessFragment.TAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //跳转到首页
                goHome();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //goHome();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goHome() {
        startActivity(MainActivity.class, true);
    }
}
