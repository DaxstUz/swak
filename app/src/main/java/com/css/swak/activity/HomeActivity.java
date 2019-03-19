package com.css.swak.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;


import com.css.swak.R;
import com.css.swak.adapter.MyFragmentStatePagerAdapter;
import com.css.swak.fragment.EarnFragment;
import com.css.swak.fragment.LinkFragment;
import com.css.swak.fragment.MyFragment;
import com.css.swak.fragment.VideoFragment;
import com.css.swak.fragment.WorkFragment;
import com.css.swak.widget.MyTabView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity {


    private static final String[] TABNAME = new String[]{
            "消息", "日程", "工作","通讯录","我的"
    };

    private static final int[] TABICONS = new int[]{
            R.drawable.tab_icon_video, R.drawable.tab_icon_earn, R.drawable.tab_icon_my, R.drawable.tab_icon_my, R.drawable.tab_icon_my
    };

    private TabHost tabhost;

    private ViewPager viewpager_main;
    private MyFragmentStatePagerAdapter adpater;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initTabView();
        initViewPager();
        addListener();

        tabhost.setCurrentTab(1);
        tabhost.setCurrentTab(0);
    }

    private void initViewPager() {
        viewpager_main = findViewById(R.id.viewpager_main);
        fragments = new ArrayList<>();
        fragments.add(VideoFragment.newInstance());
        fragments.add(EarnFragment.newInstance());
        fragments.add(WorkFragment.newInstance());
        fragments.add(LinkFragment.newInstance());
        fragments.add(MyFragment.newInstance());

        adpater = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), fragments);
        viewpager_main.setAdapter(adpater);
    }

    private void initTabView() {

        tabhost = findViewById(R.id.tabhost);
        tabhost.setup();

        for (int i = 0; i < TABNAME.length; i++) {
            TabHost.TabSpec tabview = tabhost.newTabSpec(TABNAME[i]);
            MyTabView tabviewvideo = new MyTabView(this);
            tabviewvideo.setText(TABNAME[i]);
            tabviewvideo.setIcon(TABICONS[i]);

            tabview.setIndicator(tabviewvideo);
            tabview.setContent(R.id.viewpager_main);

            tabhost.addTab(tabview);
        }
    }

    public void addListener() {
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                viewpager_main.setCurrentItem(tabhost.getCurrentTab());
            }
        });

        viewpager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabhost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
