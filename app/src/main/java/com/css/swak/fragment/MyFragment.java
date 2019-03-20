package com.css.swak.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.css.swak.R;


public class MyFragment extends BaseFragment {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_my,null);
    }

    @Override
    protected void handlerDo(String str) {

    }

}
