package com.sunyard.sundemo.view.fragment;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.activity.OrderPayActivity;
import com.sunyard.sundemo.activity.PaySuccessActivity;
import com.sunyard.sundemo.common.base.BaseFragment;
import com.sunyard.sundemo.common.utils.JsonUtils;
import com.sunyard.sundemo.model.http.bean.SunBean;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by MengJie on 2017/8/24.
 */

public class ConfirmOrderFragment extends BaseFragment {
    public static final String TAG = "ConfirmOrderFragment";
    private static final String PARAM_JSON = "param_json";
    @InjectView(R.id.iv_pic)
    ImageView ivPic;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_amount)
    TextView tvAmount;
    @InjectView(R.id.radio1)
    RadioButton radio1;
    @InjectView(R.id.radio2)
    RadioButton radio2;
    @InjectView(R.id.radio3)
    RadioButton radio3;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.tv_freight_charge)
    TextView tvFreightCharge;
    @InjectView(R.id.tv_vip_amount)
    TextView tvVipAmount;
    @InjectView(R.id.rl_stage)
    RelativeLayout rlStage;
    private float vipAmount = 0.01f;

    public static ConfirmOrderFragment newInstance(String json) {

        Bundle args = new Bundle();
        args.putString(PARAM_JSON, json);
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_order, container, false);
        ButterKnife.inject(this, view);

        //get args
        Bundle args = getArguments();
        String json = args.getString(PARAM_JSON);
        SunBean.Commodity commodity = JsonUtils.toObject(json, SunBean.Commodity.class);

        //init data
        ivPic.setImageResource(commodity.imgId);
        tvName.setText(commodity.name);
        tvPrice.setText(commodity.price + " 元");
        float amount = commodity.price;
        tvAmount.setText(amount + " 元");
        tvAmount.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        tvVipAmount.setText(vipAmount + " 元");

        float stage1 = (float) (Math.round(amount / 3 * 100)) / 100;
        radio1.setText(stage1 + "x3期（0手续费）");

        float stage2 = (float) (Math.round(amount / 6 * 100)) / 100;
        radio2.setText(stage2 + "x6期（0手续费）");

        float stage3 = (float) (Math.round(amount / 12 * 100)) / 100;
        radio3.setText(stage3 + "x12期（0手续费）");


        //checked listener
        rlStage.setVisibility(View.GONE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:

                        break;
                    case R.id.radio2:

                        break;
                    case R.id.radio3:

                        break;
                    case R.id.radio4:

                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.btn_buy, R.id.radio1, R.id.radio2, R.id.radio3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                //showPayDialog();
                OrderPayActivity.actionStart(getContext());
                break;
        }
    }

    private void showPayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("SunyardCredit支付");
        builder.setMessage("确认付款");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //跳转到付款成功页面
                startActivity(PaySuccessActivity.class, true);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
