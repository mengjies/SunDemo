package com.sunyard.sundemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunyard.sundemo.R;
import com.sunyard.sundemo.activity.ConformOrderActivity;
import com.sunyard.sundemo.common.base.BaseFragment;
import com.sunyard.sundemo.common.utils.DisplayUtils;
import com.sunyard.sundemo.common.utils.JsonUtils;
import com.sunyard.sundemo.contract.DetailContract;
import com.sunyard.sundemo.model.bean.SunBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by MengJie on 2017/8/24.
 */

public class DetailFragment extends BaseFragment implements DetailContract.View {
    public static final String TAG = "DetailFragment";
    private static final String ARG_JSON = "arg_json";
    @InjectView(R.id.iv_pic)
    ImageView ivPic;
    @InjectView(R.id.iv_detail1)
    ImageView ivDetail1;
    @InjectView(R.id.iv_detail2)
    ImageView ivDetail2;
    @InjectView(R.id.iv_detail3)
    ImageView ivDetail3;
    @InjectView(R.id.clProgressBar)
    ContentLoadingProgressBar clProgressBar;
    @InjectView(R.id.btn_buy)
    Button btnBuy;
    private DetailContract.Presenter mPresenter;
    private SunBean.Commodity commodity;
    private View rootView;
    private String json;

    public static DetailFragment newInstance(String json) {

        Bundle args = new Bundle();
        args.putString(ARG_JSON, json);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, rootView);

        //init probar
        clProgressBar.show();

        //get args
        json = getArguments().getString(ARG_JSON);
        commodity = JsonUtils.toObject(json, SunBean.Commodity.class);

        int size = DisplayUtils.getScreenWidth(getContext());

        Picasso.with(getContext()).load(commodity.imgId).resize(size, size).into(ivPic);
        Picasso.with(getContext()).load(commodity.imgDetail1).resize(size, size).into(ivDetail1);
        Picasso.with(getContext()).load(commodity.imgDetail2).resize(size, size).into(ivDetail2);
        Picasso.with(getContext()).load(commodity.imgDetail3).resize(size, size).into(ivDetail3);

        clProgressBar.hide();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.btn_buy)
    public void onClick() {
        //购买
        showPopupBuy();
    }

    private void showPopupBuy() {
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_buy, null);
        final PopupWindow pw = new PopupWindow(getContext());
        pw.setContentView(popupView);

        ImageView ivPic = (ImageView) popupView.findViewById(R.id.iv_pic);
        TextView tvName = (TextView) popupView.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) popupView.findViewById(R.id.tv_price);
        Button btnAffirm = (Button) popupView.findViewById(R.id.btn_affirm);
        ivPic.setImageResource(commodity.imgId);
        tvName.setText(commodity.name);
        tvPrice.setText(commodity.price + "元");
        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goNext
                pw.dismiss();
                Map<String, String> map = new HashMap<>();
                map.put(ConformOrderActivity.PARAM_JSON, json);
                startActivity(ConformOrderActivity.class, map);
            }
        });

        pw.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //取消半透明
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //show
        pw.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        //背景半透明
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.6f;
        getActivity().getWindow().setAttributes(lp);

    }
}
