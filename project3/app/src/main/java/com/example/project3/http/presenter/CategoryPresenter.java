package com.example.project3.http.presenter;

import android.util.Log;

import com.example.project3.entity.CategoryEntity;
import com.example.project3.entity.CategoryEntity2;
import com.example.project3.http.HttpMethods;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/11/3.
 */

public class CategoryPresenter extends HttpMethods {
    /**
     * 获取一级分类列表
     *
     * @param subscriber
     */
    public static void getTopList(Subscriber<List<CategoryEntity>> subscriber) {
        CategoryPresenter.getInstance();
        if(categoryService == null){
            Log.e("错误：", "categoryService为空！");
        }
        Observable<List<CategoryEntity>> observable = categoryService.getTopList()
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable, subscriber);
        Log.i("qqq", String.valueOf(observable));
    }



    /**
     * 获取二级分类列表
     *
     * @param parentId
     */
    public static void getSecondList(Subscriber subscriber, int parentId) {
        CategoryPresenter.getInstance();
        Observable<List<CategoryEntity>> observable = categoryService.getSecondList(parentId)
                .map(new HttpResultFunc<List<CategoryEntity>>());
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取商品二级列表的Observable对象
     *
     * @param parentId
     * @return
     */
    public static Observable getSecondListObservable(int parentId) {
        Observable<List<CategoryEntity>> observable = categoryService.getSecondList(parentId)
                .map(new HttpResultFunc<List<CategoryEntity>>());
        return observable;
    }

    /**
     * 获取商品全部分类信息,未测试
     */
    public static void getCategoryList(Subscriber subscriber) {
        Observable<Observable<CategoryEntity2>> observable = categoryService.getTopList()
                .map(new HttpResultFunc<List<CategoryEntity>>())
                .flatMap(new Func1<List<CategoryEntity>, Observable<Observable<CategoryEntity2>>>() {
                    @Override
                    public Observable<Observable<CategoryEntity2>> call(List<CategoryEntity> entities) {
                        return Observable
                                .from(entities) //遍历一级列表
                                .map(new Func1<CategoryEntity, Observable<CategoryEntity2>>() {
                                    @Override
                                    public Observable<CategoryEntity2> call(final CategoryEntity categoryEntity) {
                                        int cat_id = categoryEntity.getCat_id();
                                        return categoryService.getSecondList(cat_id) //根据一级列表的id查询相应的二级列表
                                                .map(new HttpResultFunc<List<CategoryEntity>>())
                                                .map(new Func1<List<CategoryEntity>, CategoryEntity2>() {
                                                    @Override
                                                    public CategoryEntity2 call(List<CategoryEntity>
                                                                                        categoryEntities) {
                                                        CategoryEntity2 entity2 = new CategoryEntity2();
                                                        entity2.setName(categoryEntity.getName());
                                                        entity2.setCat_id(categoryEntity.getCat_id());
                                                        entity2.setEntities(categoryEntities);
                                                        return entity2;
                                                    }
                                                });
                                    }
                                });
                    }
                });
        toSubscribe(observable, subscriber);
    }
}
