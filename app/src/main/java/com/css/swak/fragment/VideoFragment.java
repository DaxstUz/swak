package com.css.swak.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.css.swak.R;


public class VideoFragment extends Fragment {

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
//        Bundle args = new Bundle();
//        args.putString("label", fragmentLabel);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_video, container, false);
        return rootView;
    }
}
