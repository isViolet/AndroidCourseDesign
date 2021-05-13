package com.example.project3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project3.R;

import butterknife.ButterKnife;

public class CartFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_changge_password, null);
        ButterKnife.bind(this, view);


        return view;
    }

}
