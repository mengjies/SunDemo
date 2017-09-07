package com.sunyard.sundemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunyard.sundemo.R;
import com.sunyard.sundemo.common.utils.DisplayUtils;
import com.sunyard.sundemo.model.bean.SunBean;

import java.util.List;

/**
 * Created by MengJie on 2017/8/23.
 */

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolder> {

    private Context context;
    private List<SunBean.Commodity> dataList;
    private int imageSize;

    public CommodityAdapter(List<SunBean.Commodity> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        imageSize = (DisplayUtils.getScreenWidth(context) - DisplayUtils.dip2px(context, 30))/2;
        View view = LayoutInflater.from(context).inflate(R.layout.item_commodity, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        vh.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SunBean.Commodity commodity = dataList.get(position);
        Picasso.with(context).load(commodity.imgId).resize(imageSize, imageSize).into(holder.ivPic);
        holder.tvName.setText(commodity.name);
        holder.tvPrice.setText(commodity.price + "元");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView ivPic;
        TextView tvName, tvPrice;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivPic = (ImageView) view.findViewById(R.id.iv_pic);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
        }
    }

    private AdapterListener listener;

    public void setAdapterListener(AdapterListener listener) {
        this.listener = listener;
    }
    public interface AdapterListener {
        void onItemClick(int position);
    }



}
