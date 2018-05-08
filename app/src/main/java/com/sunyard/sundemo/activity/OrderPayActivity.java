package com.sunyard.sundemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.common.base.ActivityUtils;
import com.sunyard.sundemo.model.SunRepository;
import com.sunyard.sundemo.presenter.OrderPayPresenter;
import com.sunyard.sundemo.view.fragment.OrderPayFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OrderPayActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private ActionBar actionBar;

    public static void actionStart(Context ctx) {
        Intent intent = new Intent(ctx, OrderPayActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        ButterKnife.inject(this);
        //init title
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("支付订单");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        OrderPayFragment orderPayFragment = OrderPayFragment.newInstance();
        ActivityUtils.addFragmentToFrame(getSupportFragmentManager(), orderPayFragment, R.id.orderPayFrame, OrderPayFragment.TAG);

        new OrderPayPresenter(orderPayFragment, new SunRepository());
    }
}
