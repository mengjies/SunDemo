package com.sunyard.sundemo.model.bean;

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

}
