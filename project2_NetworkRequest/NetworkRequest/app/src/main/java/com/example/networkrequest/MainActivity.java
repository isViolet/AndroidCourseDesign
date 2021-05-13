package com.example.networkrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_Request;
    private TextView tv_result;
    private Handler handler;
    private String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new Thread(){
            public void run() {
//                        httpRequest("ww", "123456");
                getDouBan1();
            }
        }.start();
    }

    private void initView() {
        bt_Request = findViewById(R.id.bt_request);
        tv_result = findViewById(R.id.tv_result);

        bt_Request.setOnClickListener(this);

        handler = new Handler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_request:
                new Thread(){
                    public void run() {
//                        httpRequest("ww", "123456");
                        getDouBan1();
                    }
                }.start();

                break;
        };
    }

    private void getDouBan1() {
        //1,创建OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2,创建一个Request
        Request request = new Request.Builder().url("http://39.96.75.21:8001/eduservice/teacher/findAll").build();
        //3,创建一个call对象
        Call call = okHttpClient.newCall(request);
        //4,将请求添加到调度中
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
//                        Log.i("acsac","" + response.body().string());
                        final String result = response.body().string();
                        Log.i(TAG,"1"+result);
                        Entity entity = JSON.parseObject(result,Entity.class);
                        Log.i(TAG,"2"+entity.getData().getItems().size());
//                        Gson gson = new Gson();
//                        final Entity root = gson.fromJson(result,Entity.class);
//                        Log.i(TAG,"2" + root);
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (root != null && root.getData().getItems() != null){
//                                    tv_result.setText(
//                                            String.format("用户名：%s\n邮箱：%s",
//                                                    root.getData().getItems().get(0).getTitle(),
//                                                    root.getData().getItems().get(0).getTitle())
//                                    );
//                                }
//                            }
//                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 复杂json格式字符串与JavaBean_obj之间的转换
     */
//    public static void testComplexJSONStrToJavaBean(){
//
//        Entity teacher = JSON.parseObject(COMPLEX_JSON_STR, new TypeReference<Teacher>() {});
//        //Teacher teacher1 = JSON.parseObject(COMPLEX_JSON_STR, new TypeReference<Teacher>() {});//因为JSONObject继承了JSON，所以这样也是可以的
//        String teacherName = teacher.getTeacherName();
//        Integer teacherAge = teacher.getTeacherAge();
//        Course course = teacher.getCourse();
//        List<Student> students = teacher.getStudents();
//    }


    private void httpRequest(final String user_name, final String pwd) {
        new Thread(){
            private Request request;

            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient.Builder()
                        .readTimeout(5, TimeUnit.SECONDS)
                        .build();
                RequestBody body = new FormBody.Builder()
                        .add("input", user_name)
                        .add("password", pwd)
                        .build();
                Log.i(TAG, "" + body);
                request = new Request.Builder()
                        .url("http://10.216.22.238:8999/MobileShop/member/login2")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.i(TAG,""+response);
                    final String result = response.body().toString();
                    Log.i(TAG,""+result);
                    Gson gson = new Gson();
                    final MemberResult root = gson.fromJson(result,MemberResult.class);
                    Log.i(TAG,"" + root);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (root != null && root.getMemberEntity() != null){
                                tv_result.setText(
                                        String.format("用户名：%s\n邮箱：%s",
                                                root.memberEntity.user_name,
                                                root.memberEntity.email)
                                );
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
}

