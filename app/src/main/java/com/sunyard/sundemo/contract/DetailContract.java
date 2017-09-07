package com.sunyard.sundemo.contract;

import com.sunyard.sundemo.common.base.BasePresenter;
import com.sunyard.sundemo.common.base.BaseView;

/**
 * Created by MengJie on 2017/8/24.
 */

public class DetailContract {

    public interface Presenter extends BasePresenter {
    }

    public interface View extends BaseView<Presenter> {
    }


}
