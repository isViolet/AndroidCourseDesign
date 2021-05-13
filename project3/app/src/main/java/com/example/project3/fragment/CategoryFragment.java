package com.example.project3.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.R;
import com.example.project3.activity.GoodsListActivity;
import com.example.project3.adapter.CategoryLeftListAdapter;
import com.example.project3.adapter.CategoryRightListAdapter;
import com.example.project3.adapter.OnRecyclerViewItemClickListener;
import com.example.project3.common.Constants;
import com.example.project3.entity.CategoryEntity;
import com.example.project3.http.presenter.CategoryPresenter;
import com.example.project3.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class CategoryFragment extends BaseFragment {

    //返回图标
    private ImageView titleBack;
    //搜索关键词
    private TextView searchKeyword;
    //搜索布局
    private RelativeLayout searchLayout;
    //左右列表
    private RecyclerView leftList;
    private RecyclerView rightList;
    private List<CategoryEntity> leftData = new ArrayList<>();
    private List<CategoryEntity> rightData = new ArrayList<>();

    private CategoryLeftListAdapter leftAdapter;
    private CategoryRightListAdapter rightAdapter;

    private int[] good_img = {R.drawable.a,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
    private String[] good_dec = {"猕猴桃","商品2","商品3","苹果","橙子"};
    private String[] good_type = {"水果","服饰","数码","影视","日常"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        initViews();
        return view;
    }

    private void initView(View view){

        titleBack = view.findViewById(R.id.title_back);
        searchKeyword = view.findViewById(R.id.search_keyword);
        searchKeyword.setText("苹果");
        searchKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra("keyword", searchKeyword.getText().toString().trim());
                startActivity(intent);
            }
        });
        searchLayout = view.findViewById(R.id.search_layout);
        leftList = view.findViewById(R.id.left_list);
        rightList = view.findViewById(R.id.right_list);

        for (int i = 0; i<5;i++){
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setBriefGoodsType("1");
            categoryEntity.setCat_id(i);
            categoryEntity.setName(good_dec[i]);
            categoryEntity.setCat_path("1");
            categoryEntity.setGoods_count(i);
            categoryEntity.setImage(good_img[i]);
            categoryEntity.setParent_id(i);
            rightData.add(categoryEntity);
//            leftData.add(categoryEntity);
        }

        for (int i = 0; i<5;i++){
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setBriefGoodsType("1");
            categoryEntity.setCat_id(i);
            categoryEntity.setName(good_type[i]);
            categoryEntity.setCat_path("1");
            categoryEntity.setGoods_count(i);
            categoryEntity.setImage(good_img[i]);
            categoryEntity.setParent_id(i);
//            rightData.add(categoryEntity);
            leftData.add(categoryEntity);
        }
    }

    @SuppressLint("WrongConstant")
    private void initViews() {
        //调整搜索栏的样式
        titleBack.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, AndroidUtils.dp2px(this.getActivity(), 30));
        layoutParams.setMargins(10, 3, 10, 0);
        searchLayout.setLayoutParams(layoutParams);
        //设置列表样式
        LinearLayoutManager leftManager = new LinearLayoutManager(getActivity());
        leftManager.setOrientation(OrientationHelper.VERTICAL);
        //垂直表格
        @SuppressLint("WrongConstant") GridLayoutManager rightManager = new GridLayoutManager(getActivity(),
                Constants.SPAN_COUNT,
                OrientationHelper.VERTICAL, false);
        leftList.setLayoutManager(leftManager);
        rightList.setLayoutManager(rightManager);
        //适配数据
        leftAdapter = new CategoryLeftListAdapter(getActivity(), leftData);
        rightAdapter = new CategoryRightListAdapter(getActivity(), rightData);
        leftList.setAdapter(leftAdapter);
        rightList.setAdapter(rightAdapter);
//        加载左侧列表数据和item0对应的右侧列表数据
        CategoryPresenter.getTopList(new Subscriber<List<CategoryEntity>>() {
            @Override
            public void onCompleted() {
                Log.i("qqq","xxx");
            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(List<CategoryEntity> categoryEntities) {
                if (categoryEntities.size() > 0) {
                    Log.i("qqq","xxx");
                    leftData.addAll(categoryEntities);
                    leftAdapter.notifyDataSetChanged();
                    //载入item0的右侧列表数据
                    int cat_id = categoryEntities.get(0).getCat_id();
                    loadRight(cat_id);
                    //默认选中第一项
                    leftAdapter.setSelection(cat_id);
                }
                Log.i("qqq","111");
            }
        });
        //左侧列表点击事件
        leftAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryEntity entity) {
               /* Toast.makeText(getActivity(), "name:" + entity.getName() + "\r\ncat_id:" + entity.getCat_id(), Toast
                        .LENGTH_SHORT)
                        .show();*/
                //加载右侧数据
                loadRight(entity.getCat_id());
                leftAdapter.setSelection(entity.getCat_id());
            }
        });
        //右侧列表点击事件
        rightAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryEntity entity) {
               /* Toast.makeText(getActivity(), "name:" + entity.getName() + "\r\ncat_id:" + entity.getCat_id(), Toast
                        .LENGTH_SHORT)
                        .show();*/
                //跳转到商品列表界面
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra("cat_id", entity.getCat_id());
                startActivity(intent);
            }
        });
    }

    private void loadRight(int cat_id) {
        CategoryPresenter.getSecondList(new Subscriber<List<CategoryEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<CategoryEntity> categoryEntities) {


                if (categoryEntities.size() > 0) {

                    for(int i = 0; i < categoryEntities.size(); i++){
                        Log.i("CategoryFragment", "rightlist-->" + categoryEntities.get(i).toString());
                    }
                    rightData.clear();
                    rightData.addAll(categoryEntities);
                    rightAdapter.notifyDataSetChanged();
                }
            }
        }, cat_id);
    }

}
