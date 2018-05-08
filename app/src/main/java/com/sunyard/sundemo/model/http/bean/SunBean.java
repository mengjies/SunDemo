package com.sunyard.sundemo.model.http.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MengJie on 2017/8/23.
 */

public class SunBean {

    public static class RspCommodityList{
        public List<Commodity> dataList = new ArrayList<>();
    }

    public static class Commodity{
        public String name = "";
        public float price;
        public int imgId;
        public int imgDetail1;
        public int imgDetail2;
        public int imgDetail3;

        public Commodity(String name, float price, int imgId, int imgDetail1, int imgDetail2, int imgDetail3) {
            this.name = name;
            this.price = price;
            this.imgId = imgId;
            this.imgDetail1 = imgDetail1;
            this.imgDetail2 = imgDetail2;
            this.imgDetail3 = imgDetail3;
        }
    }

    /**
     * 创建订单接口
     */
    public static class ReqCreateOrder {
        public String userId = "1001";
        public int orderChannel;//支付渠道0支付宝1微信2苹果内购(Integer)
        public String orderContent = "购买商品";//购买内容
        public float orderAmount;//订单金额(Double)

    }

    /**
     * 创建订单接口
     */
    public static class RspCreateOrder {
        public OrderDetail orderDetail;
        public Object pay;
    }

    /**
     * 购买金币成功之后确认接口
     */
    public static class ReqVerifyPayResult {
        public String orderNo;//订单号
        public String orderToken = ""; // md5加密（orderNo + userId）

    }

    /**
     * 购买金币成功之后确认接口
     */
    public static class RspVerifyPayResult {

    }



    /**
     * 订单详情
     */
    public static class OrderDetail {
        public String orderNo;//订单号
        public int userId;//购买人主键id(Integer)
        public long createTime;//创建时间
        public long updateTime;//修改时间
        public String outOrderNo;//外部订单号
        public int orderStatus;//订单状态(Integer)
        public String createIp;//创建ip
        public int orderChannel;//支付渠道0支付宝1微信2苹果内购(Integer)
        public String orderContent;//购买内容
        public float orderAmount;//订单金额(Double)

    }

    /**
     * 微信支付
     */
    public static class WechatPay {

        public String sign;
        public String timestamp;
        public String noncestr;
        public String partnerid;
        public String prepayid;
        @SerializedName("package")
        public String packageX;
        public String appid;
    }

    /**
     * 支付
     */
    public static class AliPay {
        public String body;// 例：购买金币
        public String _input_charset;// 例：utf-8
        public String it_b_pay;// 例：15m
        public String total_fee;// 例：0.01
        public String subject;// 例：趣陪购买金币服务
        public String notify_url;// 例：wangqiaosai.vicp.hk:18165/notify/alipayNotify
        public String service;// 例：mobile.securitypay.pay
        public String seller_id;// 例：277841278@qq.com
        public String partner;// 例：2088021402797428
        public String out_trade_no;// 例：G201704121039496577117
        public String payment_type;// 例：趣陪购买金币服务",
        public String rsa_key;// 例：MIICeAIBADANBgkqhki...

    }

}
