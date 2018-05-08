package com.sunyard.sundemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.common.base.ActivityUtils;
import com.sunyard.sundemo.common.base.BaseActivity;
import com.sunyard.sundemo.common.utils.JsonUtils;
import com.sunyard.sundemo.model.http.bean.SunBean;
import com.sunyard.sundemo.view.fragment.ConfirmOrderFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ConformOrderActivity extends BaseActivity {

    public static final String PARAM_JSON = "param_json";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private SunBean.Commodity commodity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_order);
        ButterKnife.inject(this);

        //get intent
        String json = getIntent().getStringExtra(PARAM_JSON);
        commodity = JsonUtils.toObject(json, SunBean.Commodity.class);

        //init toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("确认订单");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //load fragment
        ConfirmOrderFragment fragment = ConfirmOrderFragment.newInstance(json);
        ActivityUtils.addFragmentToFrame(getSupportFragmentManager(), fragment, R.id.conformOrderFrame, ConfirmOrderFragment.TAG);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
