package com.sunyard.sundemo.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunyard.sundemo.R;


/**
 * Created by MengJie on 2017/9/5.
 */

public class CommonView extends RelativeLayout {

    private static final int DEF_TEXT_COLOR = Color.GRAY;
    private static final float DEF_TEXT_SIZE_SP = 15;
    //value
    private int mTextColor;
    private float mTextSize;
    private String mEmptyText;
    private Bitmap mErrorImage;
    private String mErrorText;
    private Bitmap mEmptyImage;
    private ContentLoadingProgressBar clProBar;
    //view
    private LinearLayout llEmpty, llError;
    private ImageView ivEmpty, ivError;
    private TextView tvEmpty, tvError;

    public CommonView(Context context) {
        this(context, null, 0);
    }

    public CommonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CommonView, defStyleAttr, 0);

        try {
            /*mTextColor = ta.getColor(R.styleable.CommonView_textColor, DEF_TEXT_COLOR);
            float defTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEF_TEXT_SIZE_SP,
                    getResources().getDisplayMetrics());
            mTextSize = ta.getDimensionPixelSize(R.styleable.CommonView_textSize, (int) defTextSize);*/
            mEmptyText = ta.getString(R.styleable.CommonView_emptyText);
            mErrorText = ta.getString(R.styleable.CommonView_errorText);
            mEmptyImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.CommonView_emptyImage, 0));
            mErrorImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.CommonView_errorImage, 0));
        } finally {
            ta.recycle();
        }

        init(context);

    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common, this, true);
        clProBar = (ContentLoadingProgressBar) findViewById(R.id.clProgressBar);
        llEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        llError = (LinearLayout) findViewById(R.id.ll_error);
        ivEmpty = (ImageView) findViewById(R.id.iv_empty);
        ivError = (ImageView) findViewById(R.id.iv_error);
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        tvError = (TextView) findViewById(R.id.tv_error);

        clProBar.setVisibility(GONE);
        llEmpty.setVisibility(GONE);
        llError.setVisibility(GONE);

        if (mEmptyImage != null) {
            ivEmpty.setImageBitmap(mEmptyImage);
        }
        if (mErrorImage != null) {
            ivError.setImageBitmap(mErrorImage);
        }
        /*tvEmpty.setTextSize(mTextSize);
        tvEmpty.setTextColor(mTextColor);
        tvError.setTextSize(mTextSize);
        tvError.setTextColor(mTextColor);*/

        if (!TextUtils.isEmpty(mEmptyText)) {
            tvEmpty.setText(mEmptyText);
        }
        if (!TextUtils.isEmpty(mErrorText)) {
            tvError.setText(mErrorText);
        }

        llEmpty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideView();
                listener.onEmptyClick();
            }
        });

        llError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideView();
                listener.onErrorClick();
            }
        });

    }

    public void showProBar() {
        clProBar.setVisibility(VISIBLE);
        clProBar.show();
    }

    public void hideProBar() {
        clProBar.setVisibility(GONE);
        clProBar.hide();
    }

    public void showError() {
        llError.setVisibility(VISIBLE);
        llEmpty.setVisibility(GONE);
    }

    public void showEmpty() {
        llEmpty.setVisibility(VISIBLE);
        llError.setVisibility(GONE);
    }

    private void hideView() {
        llEmpty.setVisibility(GONE);
        llError.setVisibility(GONE);
    }

    private OnViewClickListener listener;

    public void setOnViewClickListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    public interface OnViewClickListener {

        void onErrorClick();

        void onEmptyClick();

    }

}
