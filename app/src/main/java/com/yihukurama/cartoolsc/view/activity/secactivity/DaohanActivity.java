package com.yihukurama.cartoolsc.view.activity.secactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.yihukurama.cartoolsc.CarToolsC;
import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.controler.MediaManager;
import com.yihukurama.cartoolsc.model.Command;
import com.yihukurama.cartoolsc.model.ConstantValue;
import com.yihukurama.cartoolsc.view.activity.BaseActivity;

import java.io.File;

public class DaohanActivity extends BaseActivity implements View.OnClickListener{
    MapView mapView;
    BaiduMap mBaiduMap;

    RelativeLayout blankRl;
    Button sousu2;
    Button cancel;
    Button keyboard;
    ImageView jilu1,jilu2,jilu3,jilu4;
    final int playVideo = 1;

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
        keyboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.sousuo2:
                //播放视频
                keyboard.setVisibility(View.VISIBLE);
                break;
            case R.id.keyboard:
                //MediaManager.playDefault(context, ConstantValue.DAOHANMEDIA);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+ File.separator+ConstantValue.DAOHANMEDIA);
                //调用系统自带的播放器
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "video/mp4");
                startActivityForResult(intent, playVideo);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode:"+requestCode+" resultCode:"+resultCode);
        if(requestCode==playVideo){
            alreadyPlayVidio=true;
        }
    }
}
