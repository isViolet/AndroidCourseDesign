package com.example.xiangmuwurenwuer.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.xiangmuwurenwuer.FavoriteGoodsEntity;
import com.example.xiangmuwurenwuer.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class FavoriteGoodsListAdapter extends RecyclerView.Adapter<FavoriteGoodsListAdapter.ViewHolder> implements View
        .OnClickListener {
    private Context mContext;
    private List<FavoriteGoodsEntity> listData;

    public OnGoodsItemClickListener mGoodsItemListener;

    public FavoriteGoodsListAdapter(Context context, List<FavoriteGoodsEntity> data) {
        this.mContext = context;
        this.listData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(listData.isEmpty()){
            View view = LayoutInflater.from(mContext).inflate(R.layout.myfavor, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, parent, false);

        view.setOnClickListener(this);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavoriteGoodsEntity entity = listData.get(position);
//        ImageLoader.getInstance().displayImage(entity.getBriefGoods().getSmall(), holder.imageView,
//                ImageLoaderManager.product_options);
        holder.title.setText(position);
        holder.price.setText("￥" + String.format("%.2f", 12.5));
        holder.itemView.setTag(entity);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onClick(View v) {
        if (mGoodsItemListener != null) {
            mGoodsItemListener.onClick(v, (FavoriteGoodsEntity) v.getTag());
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
        void onClick(View view, FavoriteGoodsEntity entity);
    }

    public void setOnGoodsItemClickListener(OnGoodsItemClickListener listener) {
        this.mGoodsItemListener = listener;
    }
}
