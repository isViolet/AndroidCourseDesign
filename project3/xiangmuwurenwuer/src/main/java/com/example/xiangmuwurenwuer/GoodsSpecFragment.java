package com.example.xiangmuwurenwuer;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsSpecFragment extends Fragment {

    //商品图片
    ImageView goodsImage;
    //商品价格
    TextView goodsPrice;
    //减少
    ImageButton btnRelease;
    //数量
    EditText goodsCount;
    //增加
    ImageButton btnAdd;
    //添加购物车
    TextView addToCart;
    private GoodsDetailEntity entity;
    private int count;
    private double totalPrice;

    public GoodsSpecFragment(){

    }

    /**
     * 创建Fragment视图
     *
     * @param inflater           布局加载器
     * @param container          布局父容器
     * @param savedInstanceState
     * @return 返回加载的View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_goods_spec, container, false);

        goodsImage = view.findViewById(R.id.goods_image);
        goodsPrice = view.findViewById(R.id.goods_price);
        btnRelease = view.findViewById(R.id.btn_release);
        goodsCount = view.findViewById(R.id.goods_count);
        btnAdd = view.findViewById(R.id.btn_add);
        addToCart = view.findViewById(R.id.add_to_cart);

//        entity  = (GoodsDetailEntity) getArguments().getSerializable("entity");
        entity = new GoodsDetailEntity();
        entity.setPrice(22);
        goodsCount.setText("" + 1);
        goodsPrice.setText("" + entity.getPrice());
        goodsImage.setBackgroundResource(R.drawable.a);
//        ImageLoader.getInstance().displayImage(entity.getBig(), goodsImage,
//                ImageLoaderManager.product_options);
        return view;
    }


//    @OnClick({R.id.btn_release, R.id.btn_add, R.id.add_to_cart})
    public void onClick(View view) {
        count = Integer.parseInt(goodsCount.getText().toString().trim());
        switch (view.getId()) {
            case R.id.btn_release:
                if (count > 1) {
                    goodsCount.setText("" + (count - 1));
                    totalPrice = entity.getPrice() * (count - 1);
                    //保留两位小数
                    goodsPrice.setText(String.format("%2.f", totalPrice));
                } else {
                    goodsCount.setText("" + 1);
                    goodsPrice.setText("" + entity.getPrice());
                }
                break;
            case R.id.btn_add:
                goodsCount.setText("" + (count + 1));
                totalPrice = entity.getPrice() * (count + 1);
                goodsPrice.setText("" + totalPrice);
                break;
            case R.id.add_to_cart:

                break;
        }
    }

}
