package com.sunyard.sundemo.presenter;

import com.sunyard.sundemo.contract.DetailContract;
import com.sunyard.sundemo.model.SunRepository;

/**
 * Created by MengJie on 2017/8/24.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    private SunRepository sunRepository;

    public DetailPresenter(DetailContract.View mView, SunRepository sunRepository) {
        this.mView = mView;
        this.sunRepository = sunRepository;
        mView.setPresenter(this);
    }
}
