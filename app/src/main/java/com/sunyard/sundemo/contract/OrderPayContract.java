package com.sunyard.sundemo.contract;

import com.sunyard.sundemo.common.base.BasePresenter;
import com.sunyard.sundemo.common.base.BaseView;
import com.sunyard.sundemo.model.http.bean.RspBase;
import com.sunyard.sundemo.model.http.bean.SunBean;

import io.reactivex.Observer;

/**
 * Created by MengJie on 2018/5/8.
 */

public class OrderPayContract {

    public interface Presenter extends BasePresenter {
        void createOrder(SunBean.ReqCreateOrder req);

        void verifyPayResult(SunBean.ReqVerifyPayResult req);
    }

    public interface View extends BaseView<Presenter> {
        Observer<? super RspBase<SunBean.RspCreateOrder>> createOrderObserver();

        Observer<? super RspBase<SunBean.RspVerifyPayResult>> verifyPayResultObserver();
    }


}
