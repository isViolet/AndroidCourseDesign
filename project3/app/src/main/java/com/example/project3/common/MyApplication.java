package com.example.project3.common;

import android.app.Application;
import android.content.Context;

import com.example.project3.db.GreenDaoManager;
import com.example.project3.http.HttpMethods;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //greenDao全局配置
        GreenDaoManager.getInstance();

        //全局配置image-loader
        ImageLoaderManager.getInstance();


        //全局配置Retrofit
        HttpMethods.getInstance();
    }

    public static Context getContext() {
        return mContext;
    }
}
