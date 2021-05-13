package com.example.xiangmuwurenwuer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.xiangmuwurenwuer.activity.BaseActivity;
import com.example.xiangmuwurenwuer.activity.FavoriteGoodsListAdapter;
import com.example.xiangmuwurenwuer.activity.GoodsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyFavoriteActivity extends BaseActivity {

    ImageView titleBack;
    ListView productList;
    Button favoriteIndexBtn;
    LinearLayout favoriteEmpty;

    private FavoriteGoodsListAdapter adapter;
    private List<FavoriteGoodsEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        titleBack = findViewById(R.id.title_back);
        productList = findViewById(R.id.product_list);
        favoriteIndexBtn =findViewById(R.id.favorite_index_btn);
        favoriteEmpty = findViewById(R.id.favorite_empty);

//        SharedPreferences sp = getSharedPreferences("user", 0);
//        int memberId = sp.getInt("member_id", -1);
//        requestList(memberId);

//        for (int i =0;i<3 ; i++){
//            FavoriteGoodsEntity favoriteGoodsEntity = new FavoriteGoodsEntity();
//            data.add(favoriteGoodsEntity);
//        }

        String[] names = {"三浅陶社 奶喵喵陶瓷咖啡杯 手工手绘情侣杯 碟下午茶茶杯茶具",
                "依诺维绅REBEL书房双人布艺沙发床库特 小户型客厅阳台可折叠两用",
                "吱音原创 信凳创意北欧设计水曲柳全实木矮凳子家具个性板凳",
                "创意立体可爱萌系动物陶瓷杯子马克杯带盖勺牛奶杯情侣茶水咖啡杯"};
        String[] price = {"¥160.0","¥6610.0","¥499.0","¥19.8"};
        int[] img = {R.drawable.aa,R.drawable.bb,R.drawable.cc,R.drawable.dd};
        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
        for (int i = 0; i < names.length; i++){
            Map<String,Object> map = new HashMap<>();
            map.put("names", names[i]);
            map.put("price", price[i]);
            map.put("img",img[i]);
            arrayList.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,arrayList,R.layout.item_goods_list,new String[]{"names","price","img"},new int[]{R.id.goodslist_name,R.id.goodslist_price,R.id.goodslist_img});
        productList.setAdapter(simpleAdapter);

//        adapter = new FavoriteGoodsListAdapter(this, data);
//        productList.setAdapter(adapter);
//        adapter.setOnGoodsItemClickListener(new FavoriteGoodsListAdapter.OnGoodsItemClickListener() {
//            @Override
//            public void onClick(View view, FavoriteGoodsEntity entity) {
//                Intent intent = new Intent(MyFavoriteActivity.this, GoodsActivity.class);
//                intent.putExtra("goodsId", entity.getGoods_id());
//                startActivity(intent);
//            }
//        });
//        productList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.setHeaderTitle("选择操作");
//                menu.add(0, 0, 0, "移除收藏");
//            }
//        });
    }

    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                remove(menuInfo.position -1 );
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * 取消收藏
     * @param position
     */
    private void remove(int position) {
        int likeId = data.get(position).getLike_id();
//        GoodsPresenter.deleteFavoriteGoods(new ProgressDialogSubscriber<HttpResult>(this) {
//            @Override
//            public void onNext(HttpResult httpResult) {
//                Toast.makeText(MyFavoriteActivity.this, httpResult.getMsg(), Toast.LENGTH_SHORT).show();
//            }
//        },likeId);
    }

//    @OnClick({R.id.title_back, R.id.favorite_index_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.favorite_index_btn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 请求收藏列表
     */
    private void requestList(int memberId) {
        if (memberId != -1) {
//            GoodsPresenter.getFavoriteList(new ProgressDialogSubscriber<List<FavoriteGoodsEntity>>(this) {
//                @Override
//                public void onNext(List<FavoriteGoodsEntity> favoriteGoodsEntities) {
//                    if (favoriteGoodsEntities.size() == 0) {
//                        productList.setVisibility(View.GONE);
//                        favoriteEmpty.setVisibility(View.VISIBLE);
//                    } else {
//                        favoriteEmpty.setVisibility(View.GONE);
//                        productList.setVisibility(View.VISIBLE);
//                        //更新数据
//                        data.clear();
//                        data.addAll(favoriteGoodsEntities);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }, memberId);
        }
    }
}
