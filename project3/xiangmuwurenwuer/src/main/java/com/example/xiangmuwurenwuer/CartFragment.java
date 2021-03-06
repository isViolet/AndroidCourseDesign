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
        getCartList(memberId);//???????????????????????????
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

        cartTotal.setText("????????????" + total);

        SharedPreferences sp = getActivity().getSharedPreferences("user", 0);
        memberId = sp.getInt("member_id", -1);

        if (!(activity instanceof MainActivity)) {
            titleBack.setVisibility(View.VISIBLE);
        } else {
            titleBack.setVisibility(View.GONE);
        }
        cartNoDataLayout.setVisibility(View.GONE);
        cartListLayout.setVisibility(View.VISIBLE);

        //??????????????????
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //??????????????????
        refreshWidget.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color
                        .holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // ????????????????????????????????????????????????????????????????????????
        refreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //?????????????????????
        refreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //????????????
                if (NetworkUtils.isNetworkAvailable(getActivity())) {//??????????????????????????????
                    getCartList(memberId);
                } else {
                    refreshWidget.setRefreshing(false);//?????????
                    Toast.makeText(getActivity(), "???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //????????????
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
                                .setTitle("???????????????")
                                .setMessage("????????????????????????????????????")
                                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int cartId = listData.get(position).getCart_id();
                                        deleteCartGoods(cartId);
                                    }
                                })
                                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
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
     * ?????????????????????
     *
     * @param cartid
     */
    private void deleteCartGoods(int cartid) {
//        GoodsPresenter.cartDelete(new ProgressDialogSubscriber<HttpResult>(getActivity()) {
//            @Override
//            public void onNext(HttpResult httpResult) {
//                if (httpResult.getStatus() == 0) {
//                    Toast.makeText(activity, "???????????????", Toast.LENGTH_SHORT).show();
//                    refreshWidget.setRefreshing(true);
//                    getCartList(memberId);
//                }
//            }
//        }, cartid);
    }

    /**
     * ???????????????????????????
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
//                    cartTotal.setText("?????????  ???" + totalPrice);
//                }
//            }, id);
        }

    }

    /**
     * ????????????????????????????????????
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
            case R.id.shopping_cart_text://??????
                if (editCartGoods.getText().equals("??????")) {
                    editCartGoods.setText("????????????");
                }
                break;
            case R.id.cart_checkout: //??????
                break;
            case R.id.cart_forward_index://????????????
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
