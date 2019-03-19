package com.css.swak.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.css.swak.R;


public class MyTabView extends LinearLayout {

    private ImageView iv_tabicon;
    private TextView iv_tabtext;

    private LayoutInflater inflater;

    public MyTabView(Context context) {
        super(context);

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tab_icon, this, true);
        iv_tabicon = view.findViewById(R.id.iv_tabicon);
        iv_tabtext = view.findViewById(R.id.iv_tabtext);
    }

    public MyTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String str) {
        iv_tabtext.setText(str);
    }

    public void setIcon(int resourceId) {
        iv_tabicon.setImageResource(resourceId);
    }
}
