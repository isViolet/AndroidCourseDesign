package com.huatec.edu.mobileshop.http;

import android.util.Log;

import com.huatec.edu.mobileshop.common.Constants;
import com.huatec.edu.mobileshop.entity.HttpResult;
import com.huatec.edu.mobileshop.http.service.CategoryService;
import com.huatec.edu.mobileshop.http.service.GoodsService;
import com.huatec.edu.mobileshop.http.service.MemberService;
import com.huatec.edu.mobileshop.http.service.OrderService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/12.
 */

public class HttpMethods {
    protected static final String BASE_URL = Constants.BASE_URL;
    private static final int DEFAULT_TIMEOUT = 5;
    protected static final String TAG = "HttpMethods";
    private Retrofit retrofit;
    private static HttpMethods mInstance;
    protected static MemberService memberService;
    protected static OrderService orderService;

    public static CategoryService categoryService;
    public static GoodsService goodsService;

    public HttpMethods() {
        if (mInstance == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            memberService = retrofit.create(MemberService.class);
            orderService = retrofit.create(OrderService.class);

            categoryService = retrofit.create(CategoryService.class);
            goodsService = retrofit.create(GoodsService.class);
        }
    }

    public static HttpMethods getInstance() {
        if (mInstance == null) {
            synchronized (HttpMethods.class) {
                if (mInstance == null) {
                    mInstance = new HttpMethods();
                }
            }
        }
        return mInstance;
    }

    /**
     * ??????????????????Http???resultCode,??????HttpResult???Data???????????????????????????subscriber
     *
     * @param <T> Subscriber???????????????????????????????????????Data?????????????????????
     */
    public static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            Log.i(TAG, "status:" + httpResult.getStatus());
            Log.i(TAG, "msg:" + httpResult.getMsg());
            Log.i(TAG, "data:" + httpResult.getData());
            return httpResult.getData();
        }
    }

    //????????????
    public static <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
