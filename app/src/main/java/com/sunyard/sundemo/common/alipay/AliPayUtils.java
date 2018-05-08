package com.sunyard.sundemo.common.alipay;

import com.sunyard.sundemo.model.http.bean.SunBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/11.
 */

public class AliPayUtils {

    /**
     * 构造支付订单参数列表
     *
     * @param pay
     * @return
     */
    public static Map<String, String> buildOrderParamMap(SunBean.AliPay pay) {
        Map<String, String> keyValues = new HashMap<>();

        keyValues.put("partner", pay.partner);
        keyValues.put("seller_id", pay.seller_id);
        keyValues.put("out_trade_no", pay.out_trade_no);
        keyValues.put("subject", pay.subject);
        keyValues.put("body", pay.body);
        keyValues.put("total_fee", pay.total_fee);
        keyValues.put("notify_url", pay.notify_url);
        keyValues.put("service", pay.service);
        keyValues.put("payment_type", pay.payment_type);
        keyValues.put("_input_charset", pay._input_charset);
        keyValues.put("it_b_pay", pay.it_b_pay);
        keyValues.put("return_url", "m.alipay.com");

        //keyValues.put("sign", pay.rsa_key);
        //keyValues.put("sign_type", "RSA");

        return keyValues;
    }

    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, false));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, false));

        return sb.toString();
    }

    /**
     * 获取当前时间
     * 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    private static String getCurrentTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=\"");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8") + "\"");
            } catch (UnsupportedEncodingException e) {
                sb.append(value + "\"");
            }
        } else {
            sb.append(value + "\"");
        }
        return sb.toString();
    }

}
