package com.sunyard.sundemo.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.orhanobut.logger.Logger;
import com.sunyard.sundemo.R;
import com.sunyard.sundemo.activity.PaySuccessActivity;
import com.sunyard.sundemo.common.alipay.AliPayResult;
import com.sunyard.sundemo.common.base.BaseFragment;
import com.sunyard.sundemo.common.utils.JsonUtils;
import com.sunyard.sundemo.common.utils.ToastUtils;
import com.sunyard.sundemo.contract.OrderPayContract;
import com.sunyard.sundemo.model.http.bean.RspBase;
import com.sunyard.sundemo.model.http.bean.SunBean;

import java.util.Map;
import java.util.concurrent.Callable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.sunyard.sundemo.common.utils.ToastUtils.showToast;

/**
 * Created by MengJie on 2018/5/8.
 */

public class OrderPayFragment extends BaseFragment implements OrderPayContract.View {

    public static final String TAG = OrderPayFragment.class.getName();
    @InjectView(R.id.tv_amount)
    TextView tvAmount;
    @InjectView(R.id.rb_alipay)
    RadioButton rbAlipay;
    @InjectView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @InjectView(R.id.rb_wechat)
    RadioButton rbWechat;
    @InjectView(R.id.ll_wechat)
    LinearLayout llWechat;
    @InjectView(R.id.bt_submit)
    Button btSubmit;
    private OrderPayContract.Presenter mPresenter;
    private int payChannel;
    private ProgressDialog pd;
    private Disposable disposable;
    private Disposable disposable2;

    @Override
    public void setPresenter(OrderPayContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static OrderPayFragment newInstance() {

        Bundle args = new Bundle();
        OrderPayFragment fragment = new OrderPayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_pay, container, false);
        ButterKnife.inject(this, view);

        rbAlipay.setChecked(true);
        rbWechat.setChecked(false);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ll_alipay, R.id.ll_wechat, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_alipay:
                rbAlipay.setChecked(true);
                rbWechat.setChecked(false);
                payChannel = 0;
                break;
            case R.id.ll_wechat:
                rbAlipay.setChecked(false);
                rbWechat.setChecked(true);
                payChannel = 1;
                break;
            case R.id.bt_submit:
                createOrder();
                //跳转到付款成功页面
                //startActivity(PaySuccessActivity.class, true);
                break;
        }
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        showLoading();
        SunBean.ReqCreateOrder req = new SunBean.ReqCreateOrder();
        req.orderAmount = 0.01f;
        req.orderChannel = payChannel;
        mPresenter.createOrder(req);
    }

    /**
     * 创建订单回调
     * @return
     */
    @Override
    public Observer<? super RspBase<SunBean.RspCreateOrder>> createOrderObserver() {

        return new Observer<RspBase<SunBean.RspCreateOrder>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull RspBase<SunBean.RspCreateOrder> rspBase) {
                if (!rspBase.isSuccess()) {
                    showToast(rspBase.message);
                    return;
                }
                SunBean.RspCreateOrder data = rspBase.data;

                String json = JsonUtils.toJson(data.pay);
                // 关闭加载框
                dismissLoading();
                if (data.orderDetail.orderChannel == 0) {// 支付宝
                    // 调用支付宝
                    alipay(json);
                } else if (data.orderDetail.orderChannel == 1) {// 微信
                    // 微信支付
                    wechatPay(json);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                showToast(e.getMessage());
                Logger.e(e.getMessage());

                dismissLoading();
            }

            @Override
            public void onComplete() {
                dismissLoading();
            }
        };
    }

    /**
     * 支付宝支付
     * @param orderInfo
     */
    private void alipay(final String orderInfo) {

        Observable.fromCallable(new Callable<Map<String, String>>() {
            @Override
            public Map<String, String> call() throws Exception {
                PayTask alipay = new PayTask(act);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                return result;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(alipayObserver());
    }

    /**
     * 支付宝支付回调
     * @return
     */
    private Consumer<? super Map<String, String>> alipayObserver() {
        return new Consumer<Map<String, String>>() {
            @Override
            public void accept(@NonNull Map<String, String> result) throws Exception {

                AliPayResult payResult = new AliPayResult((Map<String, String>) result);
                //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    showToast("支付成功");
                    // 向服务端确认支付结果
                    verifyPayResult();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    showToast(payResult.getMemo());
                }
            }
        };
    }

    /**
     * 微信支付
     * @param json
     */
    private void wechatPay(String json) {

    }

    /**
     * 确认支付结果
     */
    private void verifyPayResult() {
        SunBean.ReqVerifyPayResult req = new SunBean.ReqVerifyPayResult();
        mPresenter.verifyPayResult(req);
    }

    /**
     * 确认支付结果回调
     * @return
     */
    @Override
    public Observer<? super RspBase<SunBean.RspVerifyPayResult>> verifyPayResultObserver() {
        return new Observer<RspBase<SunBean.RspVerifyPayResult>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable2 = d;
            }

            @Override
            public void onNext(@NonNull RspBase<SunBean.RspVerifyPayResult> rspBase) {
                if (!rspBase.isSuccess()) {
                    ToastUtils.showToast(rspBase.message);
                    return;
                }
                SunBean.RspVerifyPayResult data = rspBase.data;

                //跳转到付款成功页面
                startActivity(PaySuccessActivity.class, true);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                showToast(e.getMessage());
                Logger.e(e.getMessage());

                dismissLoading();
            }

            @Override
            public void onComplete() {
                dismissLoading();
            }
        };
    }


    /**
     * 显示加载框
     */
    private void showLoading() {
        if (pd == null) {
            pd =  new ProgressDialog(getContext());
        }
        pd.setMessage("加载中...");
        pd.show();
    }

    /**
     * 关闭加载框
     */
    private void dismissLoading() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
