package com.sunyard.sundemo.presenter;

import com.sunyard.sundemo.common.utils.JsonUtils;
import com.sunyard.sundemo.contract.OrderPayContract;
import com.sunyard.sundemo.model.SunRepository;
import com.sunyard.sundemo.model.http.bean.SunBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MengJie on 2018/5/8.
 */

public class OrderPayPresenter implements OrderPayContract.Presenter {

    private OrderPayContract.View mView;
    private SunRepository sunRepository;

    public OrderPayPresenter(OrderPayContract.View mView, SunRepository sunRepository) {
        this.mView = mView;
        this.sunRepository = sunRepository;
        mView.setPresenter(this);
    }


    @Override
    public void createOrder(SunBean.ReqCreateOrder req) {
        sunRepository.createOrder(JsonUtils.toJson(req))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView.createOrderObserver());
    }

    @Override
    public void verifyPayResult(SunBean.ReqVerifyPayResult req) {
        sunRepository.verifyPayResult(JsonUtils.toJson(req))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView.verifyPayResultObserver());
    }
}
