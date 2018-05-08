package com.sunyard.sundemo.model;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.common.utils.JsonUtils;
import com.sunyard.sundemo.model.http.bean.RspBase;
import com.sunyard.sundemo.model.http.bean.SunBean;
import com.sunyard.sundemo.model.http.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by MengJie on 2017/8/23.
 */

public class SunRepository {


    public Observable<SunBean.RspCommodityList> getCommodity() {

        return Observable.fromCallable(new Callable<SunBean.RspCommodityList>() {
            @Override
            public SunBean.RspCommodityList call() throws Exception {
                List<SunBean.Commodity> dataList = new ArrayList<>();
                dataList.add(new SunBean.Commodity("P1 潮爆插线板", 214.00f, R.drawable.ic_1, R.drawable.ic_1_detail1, R.drawable.ic_1_detail2, R.drawable.ic_1_detail3));
                dataList.add(new SunBean.Commodity("小狗手持吸尘器", 157.00f, R.drawable.ic_2, R.drawable.ic_2_detail1, R.drawable.ic_2_detail2, R.drawable.ic_2_detail3));
                dataList.add(new SunBean.Commodity("梦洁毛巾礼盒两面", 67.00f, R.drawable.ic_3, R.drawable.ic_3_detail1, R.drawable.ic_3_detail2, R.drawable.ic_3_detail3));
                dataList.add(new SunBean.Commodity("REMAX瑞亮 Micro usb安卓数据线", 36.00f, R.drawable.ic_4, R.drawable.ic_4_detail1, R.drawable.ic_4_detail2, R.drawable.ic_4_detail3));
                dataList.add(new SunBean.Commodity("SOHOME乐雅彩色耐热玻璃杯", 53.00f, R.drawable.ic_5, R.drawable.ic_5_detail1, R.drawable.ic_5_detail2, R.drawable.ic_5_detail3));
                dataList.add(new SunBean.Commodity("炊大皇 汤锅 T20D1", 83.00f, R.drawable.ic_6, R.drawable.ic_6_detail1, R.drawable.ic_6_detail2, R.drawable.ic_6_detail3));

                SunBean.RspCommodityList rspCommodityList = new SunBean.RspCommodityList();
                rspCommodityList.dataList.addAll(dataList);
                //sleep
                Thread.sleep(1500);
                return rspCommodityList;
            }
        });
    }

    /**
     * 创建订单
     * @param json
     * @return
     */
    public Observable<RspBase<SunBean.RspCreateOrder>> createOrder(String json) {
        return ApiClient.orderService.createOrder(JsonUtils.toBody(json));
    }

    /**
     * 确认订单支付结果
     * @param json
     * @return
     */
    public Observable<RspBase<SunBean.RspVerifyPayResult>> verifyPayResult(String json) {
        return ApiClient.orderService.verifyPayResult(JsonUtils.toBody(json));
    }
}
