package com.sunyard.sundemo.presenter;

import com.sunyard.sundemo.contract.MainContract;
import com.sunyard.sundemo.model.SunRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MengJie on 2017/8/23.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private SunRepository sunRepository;

    public MainPresenter(MainContract.View mView, SunRepository sunRepository) {
        this.mView = mView;
        this.sunRepository = sunRepository;
        mView.setPresenter(this);
    }

    @Override
    public void getData() {
        sunRepository.getCommodity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView.getCommodityObserver());
    }
}
