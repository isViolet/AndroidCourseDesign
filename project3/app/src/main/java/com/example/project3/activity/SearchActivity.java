package com.example.project3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project3.R;
import com.example.project3.adapter.SearchHistoryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.search_keyword)
    AutoCompleteTextView searchKeyword;
    @BindView(R.id.search_history_list)
    ListView searchHistoryList;
    @BindView(R.id.del_history_button)
    Button delHistoryButton;

    private SharedPreferences sharedPreferences;
    private List<String> historyList = new ArrayList<>();
    private SearchHistoryAdapter searchHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {

        searchHistoryList = findViewById(R.id.search_history_list);

        searchKeyword = findViewById(R.id.search_keyword);

        delHistoryButton = findViewById(R.id.del_history_button);
        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(this);

        List<String> list = new ArrayList<>();
        list.add("sacasc");
        list.add("sacac");

        searchHistoryAdapter = new SearchHistoryAdapter(this,list);

        sharedPreferences = getSharedPreferences("user", 0);
        //搜索历史点击事件
        searchHistoryAdapter.setOnItemClickListener(new SearchHistoryAdapter.OnHistoryItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("keyword", data);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        //载入历史记录列表
        int historySize = sharedPreferences.getInt("history_size", 0);
        for (int i = 0; i < historySize; i++) {
            historyList.add(sharedPreferences.getString("history_" + i, ""));
        }
        if (historyList.size() > 0) {
            searchHistoryAdapter = new SearchHistoryAdapter(this, list);
            String[] arr = {"苹果那种便宜又好吃","猕猴桃怎样好吃"};
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arr);
            searchHistoryList.setAdapter(arrayAdapter);
            delHistoryButton.setVisibility(View.VISIBLE);
        } else {
            delHistoryButton.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.title_back, R.id.search_btn, R.id.product_list_search_clean, R.id
            .del_history_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:   //返回
                finish();
                break;
            case R.id.search_btn: //搜索
                beginSearch();
                break;
            case R.id.del_history_button:   //删除历史记录
                deleteHistory();
                break;
        }
    }

    /**
     * 删除历史记录
     */
    private void deleteHistory() {
        int historySize = sharedPreferences.getInt("history_size", 0);
        SharedPreferences.Editor localEditor = sharedPreferences.edit();
        for (int i = 0; i < historySize; i++) {
            localEditor.remove("history_" + i);
        }
        localEditor.putInt("history_size", 0);
        localEditor.commit();
        historyList.clear();
        searchHistoryAdapter.notifyDataSetChanged();
        Toast.makeText(SearchActivity.this, "删除历史记录成功！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 开始搜索
     */
    private void beginSearch() {
        //本地验证
        String keyword = searchKeyword.getText().toString();
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(SearchActivity.this, "请输入要搜索的关键词！", Toast.LENGTH_SHORT).show();
            return;
        }
        //保存搜索记录
        int historySize = sharedPreferences.getInt("history_size", 0);
        SharedPreferences.Editor localEditor = sharedPreferences.edit();

        //取出之前的历史记录的前9条
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < historySize; i++) {
            if (tempList.size() < 9) {
                tempList.add(sharedPreferences.getString("history_" + i, ""));
            }
            localEditor.remove("history_" + i);
        }
        //增加新的历史记录到集合中
        tempList.add(0, keyword);
        //将集合中的历史记录写到本地
        for (int i = 0; i < tempList.size(); i++) {
            localEditor.putString("history_" + i, tempList.get(i));
        }
        localEditor.putInt("history_size", tempList.size());
        localEditor.commit();
        //开始搜索
        Intent returnIntent = new Intent();
        returnIntent.putExtra("keyword", keyword);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
