package com.yihukurama.cartoolsc.view.activity.secactivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.controler.MediaManager;
import com.yihukurama.cartoolsc.model.ConstantValue;
import com.yihukurama.cartoolsc.view.activity.BaseActivity;

public class DaohanActivity extends BaseActivity implements View.OnClickListener{
    MapView mapView;
    BaiduMap mBaiduMap;

    RelativeLayout blankRl;
    Button sousu2;
    Button cancel;
    Button keyboard;
    ImageView jilu1,jilu2,jilu3,jilu4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daohan);

        init();
    }

    private void init() {
        sousu2 = (Button)findViewById(R.id.sousuo2);
        cancel = (Button)findViewById(R.id.cancel);
        keyboard = (Button)findViewById(R.id.keyboard);
        jilu1 = (ImageView)findViewById(R.id.jilu1);
        jilu2 = (ImageView)findViewById(R.id.jilu2);
        jilu3 = (ImageView)findViewById(R.id.jilu3);
        jilu4 = (ImageView)findViewById(R.id.jilu4);

        blankRl = (RelativeLayout)findViewById(R.id.daohan_blank);
        blankRl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                keyboard.setVisibility(View.GONE);
                return false;
            }
        });

        sousu2.setOnClickListener(this);
        cancel.setOnClickListener(this);
        jilu1.setOnClickListener(this);
        jilu2.setOnClickListener(this);
        jilu3.setOnClickListener(this);
        jilu4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.sousuo2:
                //≤•∑≈ ”∆µ
                keyboard.setVisibility(View.VISIBLE);
                MediaManager.playDefault(context, ConstantValue.DAOHANMEDIA);
                break;
        }
    }
}
