package com.sunyard.sundemo.model.http.service;

import com.sunyard.sundemo.model.SunProtocol;
import com.sunyard.sundemo.model.http.bean.RspBase;
import com.sunyard.sundemo.model.http.bean.SunBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by MengJie on 2018/5/8.
 */

public interface OrderService {

    /**
     * 创建订单
     * @param body
     * @return
     */
    @POST(SunProtocol.port_createOrder)
    Observable<RspBase<SunBean.RspCreateOrder>> createOrder(@Body RequestBody body);


    /**
     * 确认订单支付结果
     * @param body
     * @return
     */
    @POST(SunProtocol.port_verifyPayResult)
    Observable<RspBase<SunBean.RspVerifyPayResult>> verifyPayResult(@Body RequestBody body);
}
