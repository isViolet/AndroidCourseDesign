package com.example.project3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.R;
import com.example.project3.common.ImageLoaderManager;
import com.example.project3.entity.GoodsEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> implements View
        .OnClickListener {
    private Context mContext;
    private List<GoodsEntity> listData;

    private int[] pic = {R.drawable.a,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a7};

    public OnGoodsItemClickListener mGoodsItemListener;

    public GoodsListAdapter(Context context, List<GoodsEntity> data) {
        this.mContext = context;
        this.listData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, parent, false);

        view.setOnClickListener(this);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsEntity entity = listData.get(position);
//        ImageLoader.getInstance().displayImage(entity.getSmall(), holder.imageView, ImageLoaderManager.product_options);
        holder.title.setText(entity.getName());
        holder.price.setText("￥" + String.format("%.2f", entity.getPrice()));
        holder.imageView.setBackgroundResource(pic[3]);
//        holder.itemView.setTag(entity);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onClick(View v) {
        if (mGoodsItemListener != null) {
            mGoodsItemListener.onClick(v, (GoodsEntity) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;//商品图片
        private TextView title;//商品名称
        private TextView price;//商品价格

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.goodslist_img);
            title = (TextView) itemView.findViewById(R.id.goodslist_name);
            price = (TextView) itemView.findViewById(R.id.goodslist_price);
        }
    }

    public interface OnGoodsItemClickListener {
        void onClick(View view, GoodsEntity entity);
    }

    public void setOnGoodsItemClickListener(OnGoodsItemClickListener listener) {
        this.mGoodsItemListener = listener;
    }
}
