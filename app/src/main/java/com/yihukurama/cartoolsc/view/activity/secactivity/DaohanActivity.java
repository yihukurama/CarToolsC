package com.yihukurama.cartoolsc.view.activity.secactivity;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.view.activity.BaseActivity;

public class DaohanActivity extends BaseActivity {
    MapView mapView;
    BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daohan);
    }
}
