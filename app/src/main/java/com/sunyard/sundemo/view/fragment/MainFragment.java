package com.sunyard.sundemo.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.sunyard.sundemo.R;
import com.sunyard.sundemo.activity.DetailActivity;
import com.sunyard.sundemo.common.base.BaseFragment;
import com.sunyard.sundemo.common.utils.JsonUtils;
import com.sunyard.sundemo.contract.MainContract;
import com.sunyard.sundemo.model.bean.SunBean;
import com.sunyard.sundemo.view.adapter.CommodityAdapter;
import com.sunyard.sundemo.view.widget.CommonView;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by MengJie on 2017/8/23.
 */

public class MainFragment extends BaseFragment implements MainContract.View {
    public static final String TAG = "MainFragment";
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.commonView)
    CommonView commonView;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MainContract.Presenter mPresenter;
    private List<SunBean.Commodity> dataList = new ArrayList<>();
    private CommodityAdapter adapter;
    private LoadMoreWrapper loadMoreWrapper;
    private boolean isLoadMore;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        //init common view
        //commonView.showProBar();
        commonView.setOnViewClickListener(new CommonView.OnViewClickListener() {
            @Override
            public void onErrorClick() {
                initData();
            }

            @Override
            public void onEmptyClick() {
                initData();
            }
        });

        // init data
        //initData();

        //load adapter
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommodityAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(adapter);
        loadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                    }
                });
            }
        });
        recyclerView.setAdapter(loadMoreWrapper);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        adapter.setAdapterListener(new CommodityAdapter.AdapterListener() {
            @Override
            public void onItemClick(int position) {
                SunBean.Commodity commodity = dataList.get(position);
                String json = JsonUtils.toJson(commodity);
                Map<String, String> map = new HashMap<>();
                map.put(DetailActivity.PARAM_JSON, json);
                startActivity(DetailActivity.class, map);

            }
        });


        return view;
    }

    /**
     * load more
     */
    private void loadMore() {
        isLoadMore = true;
        mPresenter.getData();
    }

    /**
     * init data
     */
    private void initData() {
        isLoadMore = false;
        mPresenter.getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public Observer<? super SunBean.RspCommodityList> getCommodityObserver() {
        return new Observer<SunBean.RspCommodityList>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull SunBean.RspCommodityList rspCommodityList) {
                // load data
                if (!isLoadMore) {
                    dataList.clear();
                }
                dataList.addAll(rspCommodityList.dataList);
                loadMoreWrapper.notifyDataSetChanged();

                //empty
                if (dataList.size() == 0) {
                    commonView.showEmpty();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.e(e.getMessage());
                stopRefresh();
                commonView.showError();
            }

            @Override
            public void onComplete() {
                stopRefresh();
            }
        };
    }

    private void stopRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
