package com.css.swak.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.css.swak.R;


public class WorkFragment extends Fragment {

    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
//        Bundle args = new Bundle();
//        args.putString("label", fragmentLabel);
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_my,null);
    }
}
