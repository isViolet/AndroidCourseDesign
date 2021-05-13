package com.example.xiangmuwurenwuer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.xiangmuwurenwuer.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment implements View.OnClickListener {
    ImageView titleBack;
    TextView editCartGoods;
    TextView checkoutButton;
    Button homePageButton;
    LinearLayout cartNoDataLayout;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshWidget;
    LinearLayout cartListLayout;
    TextView cartTotal;
    private UpdateCart receiver;

    private BaseActivity activity;
    private int memberId;
    private LinearLayoutManager mLayoutManager;
    private List<CartGoodsEntity> listData = new ArrayList<>();
    private CartGoodsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
//        ButterKnife.bind(this, view);

        titleBack = view.findViewById(R.id.title_back);
        editCartGoods = view.findViewById(R.id.shopping_cart_text);;
        checkoutButton = view.findViewById(R.id.cart_checkout);;
        homePageButton = view.findViewById(R.id.cart_forward_index);;
        cartNoDataLayout = view.findViewById(R.id.cart_no_data_layout);;
        recyclerView = view.findViewById(R.id.recyclerView_cart);;
        refreshWidget = view.findViewById(R.id.refresh_widget);;
        cartListLayout = view.findViewById(R.id.cart_list_layout);;
        cartTotal = view.findViewById(R.id.cart_total);;

        init();
        getCartList(memberId);//请求购物车列表数据
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
    }

    @SuppressLint("WrongConstant")
    private void init() {
        int total = 0;
        for(int i =0; i<3; i++){
            CartGoodsEntity entity = new CartGoodsEntity();
            CartGoodsEntity.BriefGoodsBean briefGoodsBean = new CartGoodsEntity.BriefGoodsBean();
            briefGoodsBean.setName("ascac");
            entity.setBriefGoods(briefGoodsBean);
            entity.setAmount((int)(Math.random() * 100));
            entity.setGoods_num((int)(Math.random() * 10));
            listData.add(entity);
            total = total + (int)entity.getAmount() * entity.getGoods_num();
        }

        cartTotal.setText("合计：￥" + total);

        SharedPreferences sp = getActivity().getSharedPreferences("user", 0);
        memberId = sp.getInt("member_id", -1);

        if (!(activity instanceof MainActivity)) {
            titleBack.setVisibility(View.VISIBLE);
        } else {
            titleBack.setVisibility(View.GONE);
        }
        cartNoDataLayout.setVisibility(View.GONE);
        cartListLayout.setVisibility(View.VISIBLE);

        //设置列表样式
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //设置刷新样式
        refreshWidget.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color
                        .holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        refreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //下拉刷新监听器
        refreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                if (NetworkUtils.isNetworkAvailable(getActivity())) {//有网络才允许重新刷新
                    getCartList(memberId);
                } else {
                    refreshWidget.setRefreshing(false);//无网络
                    Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //适配数据
        adapter = new CartGoodsAdapter(getActivity(), listData);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener
                        .OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //nothing
                    }

                    @Override
                    public void onLongItemClick(View view, final int position) {
                        new AlertDialog.Builder(activity)
                                .setTitle("删除购物车")
                                .setMessage("您确认要删除这种商品吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int cartId = listData.get(position).getCart_id();
                                        deleteCartGoods(cartId);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    }
                })
        );
    }

    /**
     * 删除购物车商品
     *
     * @param cartid
     */
    private void deleteCartGoods(int cartid) {
//        GoodsPresenter.cartDelete(new ProgressDialogSubscriber<HttpResult>(getActivity()) {
//            @Override
//            public void onNext(HttpResult httpResult) {
//                if (httpResult.getStatus() == 0) {
//                    Toast.makeText(activity, "删除成功！", Toast.LENGTH_SHORT).show();
//                    refreshWidget.setRefreshing(true);
//                    getCartList(memberId);
//                }
//            }
//        }, cartid);
    }

    /**
     * 获取购物车商品列表
     */
    private void getCartList(int id) {
        if (memberId != -1) {
//            GoodsPresenter.cartList(new Subscriber<List<CartGoodsEntity>>() {
//
//                @Override
//                public void onCompleted() {
//                    refreshWidget.setRefreshing(false);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    refreshWidget.setRefreshing(false);
//                }
//
//                @Override
//                public void onNext(List<CartGoodsEntity> cartGoodsEntities) {
//                    listData.clear();
//                    listData.addAll(cartGoodsEntities);
//                    adapter.notifyDataSetChanged();
//
//                    double totalPrice = 0.0;
//                    for (int i = 0; i < cartGoodsEntities.size(); i++) {
//                        totalPrice += cartGoodsEntities.get(i).getAmount();
//                    }
//                    cartTotal.setText("总价：  ￥" + totalPrice);
//                }
//            }, id);
        }

    }

    /**
     * 接收广播，同步购物车商品
     */
    private void registerReceiver() {
        activity = (BaseActivity) getActivity();
        UpdateCart receiver = new UpdateCart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.goods.shoopingcart");
        activity.registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
//        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                getActivity().finish();
                break;
            case R.id.shopping_cart_text://编辑
                if (editCartGoods.getText().equals("编辑")) {
                    editCartGoods.setText("长按删除");
                }
                break;
            case R.id.cart_checkout: //结算
                break;
            case R.id.cart_forward_index://回到首页
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("action", "toIndex");
                startActivity(intent);
                break;
        }
    }


    private class UpdateCart extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshWidget.setProgressViewOffset(false, 0, 100);
            refreshWidget.setRefreshing(true);
            getCartList(memberId);
        }
    }


}
