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
        //????????????????????????
        searchHistoryAdapter.setOnItemClickListener(new SearchHistoryAdapter.OnHistoryItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("keyword", data);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        //????????????????????????
        int historySize = sharedPreferences.getInt("history_size", 0);
        for (int i = 0; i < historySize; i++) {
            historyList.add(sharedPreferences.getString("history_" + i, ""));
        }
        if (historyList.size() > 0) {
            searchHistoryAdapter = new SearchHistoryAdapter(this, list);
            String[] arr = {"???????????????????????????","?????????????????????"};
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
            case R.id.title_back:   //??????
                finish();
                break;
            case R.id.search_btn: //??????
                beginSearch();
                break;
            case R.id.del_history_button:   //??????????????????
                deleteHistory();
                break;
        }
    }

    /**
     * ??????????????????
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
        Toast.makeText(SearchActivity.this, "???????????????????????????", Toast.LENGTH_SHORT).show();
    }

    /**
     * ????????????
     */
    private void beginSearch() {
        //????????????
        String keyword = searchKeyword.getText().toString();
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(SearchActivity.this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        //??????????????????
        int historySize = sharedPreferences.getInt("history_size", 0);
        SharedPreferences.Editor localEditor = sharedPreferences.edit();

        //?????????????????????????????????9???
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < historySize; i++) {
            if (tempList.size() < 9) {
                tempList.add(sharedPreferences.getString("history_" + i, ""));
            }
            localEditor.remove("history_" + i);
        }
        //????????????????????????????????????
        tempList.add(0, keyword);
        //???????????????????????????????????????
        for (int i = 0; i < tempList.size(); i++) {
            localEditor.putString("history_" + i, tempList.get(i));
        }
        localEditor.putInt("history_size", tempList.size());
        localEditor.commit();
        //????????????
        Intent returnIntent = new Intent();
        returnIntent.putExtra("keyword", keyword);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
