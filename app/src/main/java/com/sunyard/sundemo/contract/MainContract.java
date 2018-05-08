package com.sunyard.sundemo.contract;

import com.sunyard.sundemo.common.base.BasePresenter;
import com.sunyard.sundemo.common.base.BaseView;
import com.sunyard.sundemo.model.http.bean.SunBean;

import io.reactivex.Observer;

/**
 * Created by MengJie on 2017/8/23.
 */

public class MainContract {

    public interface Presenter extends BasePresenter {
        void getData();
    }

    public interface View extends BaseView<Presenter> {
        Observer<? super SunBean.RspCommodityList> getCommodityObserver();
    }

}
