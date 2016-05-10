package com.yihukurama.cartoolsc.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import android.graphics.Point;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolsc.CarToolsC;
import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.controler.sdk.baidu.Location.service.LocationService;
import com.yihukurama.cartoolsc.controler.utils.android.GestureUtil;
import com.yihukurama.cartoolsc.model.Command;
import com.yihukurama.cartoolsc.view.activity.secactivity.DaohanActivity;
import com.yihukurama.cartoolsc.view.activity.secactivity.GestureActivity;

import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button daohan;
    Button diantai;
    Button duomeiti;
    Button tongxun;
    Button shushi;
    TextView connectText;
    String message;

    ImageView menu_iv;

    Button yaokong_btn;
    View yaokong_view;
    LinearLayout yaokong_ll;

    Button nenghao_btn;
    View nenghao_view;
    RelativeLayout nenghao_rl;

    Button daohan_btn;
    View daohan_view;
    RelativeLayout daohan_rl;
    ImageView sousu1;
    MapView mapView;
    BaiduMap mBaiduMap;
    ImageView d3_iv;
    ImageView weizhi_iv;
    ImageView jia_iv;
    ImageView jian_iv;
    boolean isClikOpen = false;//是否点击按钮打开菜单
    private ImageView imageTemp;
    private Bitmap tempBitmap;

    SlidingMenu menu;
    Button ae86_btn;
    ImageView menuclose;

    public static boolean powerIsConnected = false;

    float bilichi = 16;
    float overlookvalue = -180;
    boolean isOverLook = false;
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    public static MainActivity instance;

    private IntentFilter mIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_menu_main);
        mCurrentMode = LocationMode.NORMAL;
        instance=this;
        prepare();
        initView();
        initData();

//        获取状态栏高度
        int height = GestureUtil.getStatusBarHeight(context);
        //获取activity和menu中的占位view。两者代码一致
        View v = findViewById(R.id.stras_padding);
        //设置占位view的高度为状态栏高度
//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
//        v.setLayoutParams(params);
        //或者可以设置padding
        v.setPadding(0, height, 0, 0);
        menu.getMenu().findViewById(R.id.stras_padding).setPadding(0, height, 0, 0);

        //电池相关
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        mIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(PowerConnectionReceiver, mIntentFilter);
    }

    private void initView() {
        daohan = (Button) findViewById(R.id.daohan);
        daohan.setOnClickListener(this);

        diantai = (Button) findViewById(R.id.cheliangfuwu);
        diantai.setOnClickListener(this);

        connectText = (TextView) findViewById(R.id.connect);
        connectText.setOnClickListener(this);

        duomeiti = (Button) findViewById(R.id.shoushi);
        duomeiti.setOnClickListener(this);

        shushi = (Button) findViewById(R.id.shezhi);
        shushi.setOnClickListener(this);

        tongxun = (Button) findViewById(R.id.yaokong);
        tongxun.setOnClickListener(this);

        menu_iv = (ImageView) findViewById(R.id.menu_iv);
        ;
        yaokong_btn = (Button) findViewById(R.id.yaokong_btn);
        yaokong_view = (View) findViewById(R.id.yaokong_view);
        yaokong_ll = (LinearLayout) findViewById(R.id.yaokong_ll);

        nenghao_btn = (Button) findViewById(R.id.nenghao_btn);
        nenghao_view = (View) findViewById(R.id.nenghao_view);
        nenghao_rl = (RelativeLayout) findViewById(R.id.nenghao_rl);

        imageTemp = (ImageView) findViewById(R.id.image_temp);
        daohan_btn = (Button) findViewById(R.id.daohan_btn);
        daohan_view = (View) findViewById(R.id.daohan_view);
        daohan_rl = (RelativeLayout) findViewById(R.id.daohan_rl);
        sousu1 = (ImageView) findViewById(R.id.sousuo1);
        mapView = (MapView) findViewById(R.id.bmapView);
        mapView.showZoomControls(false);
        d3_iv = (ImageView) findViewById(R.id.d3);
        weizhi_iv = (ImageView) findViewById(R.id.weizhi);
        jia_iv = (ImageView) findViewById(R.id.jia);
        jian_iv = (ImageView) findViewById(R.id.jian);
        jia_iv.setOnClickListener(this);
        jian_iv.setOnClickListener(this);
        d3_iv.setOnClickListener(this);
        weizhi_iv.setOnClickListener(this);

        menu_iv.setOnClickListener(this);
        yaokong_btn.setOnClickListener(this);
        nenghao_btn.setOnClickListener(this);
        daohan_btn.setOnClickListener(this);
        sousu1.setOnClickListener(this);

        initMenu();
    }

    private void initMenu() {
        /*
        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.LEFT);
        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        *//**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         *//*
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.sliding_menu);
        menuclose = (ImageView) findViewById(R.id.menu_close_iv);
        ae86_btn = (Button) findViewById(R.id.ae86_btn);
        menuclose.setOnClickListener(this);
        ae86_btn.setOnClickListener(this);*/





//        mMenuFragment = new MenuFragment();

        View view = getLayoutInflater().inflate(R.layout.sliding_menu, null, false);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_menu_frame, mMenuFragment).commit();

        menu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);
        //获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);

        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */

//        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
//            @Override
//            public void onOpened() {
//                Log.d("map", "slidingMenu is opened");
//            }
//        });

        //打开的时候截图并隐藏map
//        menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
//            @Override
//            public void onOpen() {
//                Log.d("map", "slidingMenu is opening");
//                if(!isClikOpen && daohan_rl.getVisibility()==View.VISIBLE) {
//                    mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
//                        @Override
//                        public void onSnapshotReady(Bitmap bitmap) {
//                            tempBitmap = bitmap;
//                            imageTemp.setImageBitmap(tempBitmap);
//                            mapView.setVisibility(View.GONE);
//                            imageTemp.setVisibility(View.VISIBLE);
//                            Log.d("map", tempBitmap + "");
//                        }
//                    });
//                }
//            }
//        });
        //关闭以后再还原回来
        menu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                Log.d("map", "slidingMenu is closed");
//                if (tempBitmap != null && !tempBitmap.isRecycled()) {
//                    tempBitmap.recycle();
//                    tempBitmap = null;
//                }
                mapView.setVisibility(View.VISIBLE);
                imageTemp.setVisibility(View.GONE);
                isClikOpen=false;
            }
        });
        menu.setMenu(view);

        menuclose = (ImageView) findViewById(R.id.menu_close_iv);
        ae86_btn = (Button) findViewById(R.id.ae86_btn);
        menuclose.setOnClickListener(this);
        ae86_btn.setOnClickListener(this);


    }

    private void initData() {

        // -----------location config ------------
        locationService = ((CarToolsC) this.getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mLocListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());

        mBaiduMap = mapView.getMap();
        MapStatus ms = new MapStatus.Builder().zoom(bilichi).build();

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

        locationService.start();

        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        if (tempBitmap != null && bitmap!=null && tempBitmap.equals(bitmap)) {
                        }else if(tempBitmap != null && !tempBitmap.isRecycled() && bitmap!=null){
                            tempBitmap.recycle();
                            tempBitmap = null;
                            tempBitmap = bitmap;
                            imageTemp.setImageBitmap(tempBitmap);
                        }else if(bitmap!=null){
                            tempBitmap = bitmap;
                            imageTemp.setImageBitmap(tempBitmap);
                        }
                        Log.i("snapshot","setOnMapLoadedCallback");
                    }
                });
            }
        });

//        mBaiduMap.setOnMapDrawFrameCallback(new BaiduMap.OnMapDrawFrameCallback() {
//            @Override
//            public void onMapDrawFrame(GL10 gl10, MapStatus mapStatus) {
//                mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
//                    @Override
//                    public void onSnapshotReady(Bitmap bitmap) {
//                        if (tempBitmap != null && !tempBitmap.isRecycled()) {
//                            tempBitmap.recycle();
//                            tempBitmap = null;
//                        }
//                        tempBitmap = bitmap;
//                        Log.i("snapshot","setOnMapDrawFrameCallback");
//                    }
//                });
//            }
//        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             * @param status 地图状态改变开始时的地图状态
             */
            public void onMapStatusChangeStart(MapStatus status) {
            }

            /**
             * 地图状态变化中
             * @param status 当前地图状态
             */
            public void onMapStatusChange(MapStatus status) {
            }

            /**
             * 地图状态改变结束
             * @param status 地图状态改变结束后的地图状态
             */
            public void onMapStatusChangeFinish(MapStatus status) {
                mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        if (tempBitmap != null && bitmap != null && tempBitmap.equals(bitmap)) {
                        } else if (tempBitmap != null && !tempBitmap.isRecycled() && bitmap != null) {
                            tempBitmap.recycle();
                            tempBitmap = null;
                            tempBitmap = bitmap;
                            imageTemp.setImageBitmap(tempBitmap);
                        } else if (bitmap != null) {
                            tempBitmap = bitmap;
                            imageTemp.setImageBitmap(tempBitmap);
                        }
                        Log.i("snapshot", "onMapStatusChangeFinish");
                    }
                });
            }
        });

        try{
            CarToolsC.bluetoothCS.startClient();
        }catch (Exception e){

        }
    }

    private void prepare() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectText.setText(CarToolsC.LinkDetectedHandler.getMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        CarToolsC.LinkDetectedHandler.setMessage(message);
        CarToolsC.LinkDetectedHandler.setConnectText(connectText);
        CarToolsC.LinkDetectedHandler.setLianjieBtn(ae86_btn);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(PowerConnectionReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(PowerConnectionReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:

                break;
            case R.id.daohan:

                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPDAOHAN);
                startActivity(new Intent(this, DaohanActivity.class));
                break;
            case R.id.cheliangfuwu:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPDIANTAI);
                break;
            case R.id.shezhi:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPSHUSHI);
                break;
            case R.id.yaokong:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPTONGXUN);
                try {
                    CarToolsC.bluetoothCS.shutdownClient();
                } catch (Exception e) {

                }
                CarToolsC.bluetoothCS.startClient();
                break;
            case R.id.shoushi:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPDUOMEITI);
                startActivity(new Intent(this, GestureActivity.class));
                break;

            case R.id.menu_iv:
                if(daohan_rl.getVisibility()==View.VISIBLE) {
                    Log.d("map", "slidingMenu is opening by click");
//                    mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
//                        @Override
//                        public void onSnapshotReady(Bitmap bitmap) {
//                            tempBitmap = bitmap;
//                    if(tempBitmap!=null)
//                            imageTemp.setImageBitmap(tempBitmap);
                        mapView.setVisibility(View.GONE);
                            imageTemp.setVisibility(View.VISIBLE);
//                            Log.d("map", tempBitmap + "");
//                        }
//                    });
                }
                isClikOpen=true;
                if (menu.isMenuShowing())
                    menu.toggle();
                else menu.showMenu();

//                Intent intent1 = new Intent(context, GestureActivity.class);
//                context.startActivity(intent1);
                break;
            case R.id.menu_close_iv:
                if (menu.isMenuShowing())
                    menu.toggle();
                break;
            case R.id.ae86_btn:
                try {
                    CarToolsC.bluetoothCS.shutdownClient();
                } catch (Exception e) {

                }
                CarToolsC.bluetoothCS.startClient();
                break;
            case R.id.yaokong_btn:
                yaokong_btn.setTextColor(0xffffffff);
                nenghao_btn.setTextColor(0xff68728A);
                daohan_btn.setTextColor(0xff68728A);
                yaokong_view.setVisibility(View.VISIBLE);
                nenghao_view.setVisibility(View.INVISIBLE);
                daohan_view.setVisibility(View.INVISIBLE);
                yaokong_ll.setVisibility(View.VISIBLE);
                nenghao_rl.setVisibility(View.GONE);
                daohan_rl.setVisibility(View.GONE);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                break;
            case R.id.nenghao_btn:
                yaokong_btn.setTextColor(0xff68728A);
                nenghao_btn.setTextColor(0xffffffff);
                daohan_btn.setTextColor(0xff68728A);
                yaokong_view.setVisibility(View.INVISIBLE);
                nenghao_view.setVisibility(View.VISIBLE);
                daohan_view.setVisibility(View.INVISIBLE);
                yaokong_ll.setVisibility(View.GONE);
                nenghao_rl.setVisibility(View.VISIBLE);
                daohan_rl.setVisibility(View.GONE);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                break;
            case R.id.daohan_btn:
                yaokong_btn.setTextColor(0xff68728A);
                nenghao_btn.setTextColor(0xff68728A);
                daohan_btn.setTextColor(0xffffffff);
                yaokong_view.setVisibility(View.INVISIBLE);
                nenghao_view.setVisibility(View.INVISIBLE);
                daohan_view.setVisibility(View.VISIBLE);
                yaokong_ll.setVisibility(View.GONE);
                nenghao_rl.setVisibility(View.GONE);
                daohan_rl.setVisibility(View.VISIBLE);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                break;
            case R.id.sousuo1:
                Intent intent = new Intent(MainActivity.this, DaohanActivity.class);
                startActivity(intent);
                break;
            case R.id.d3:
                //播放视频
//                MediaManager.playDefault(context, ConstantValue.DAOHANMEDIA);
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.);
//                mBaiduMap.setMapType();

                if (isOverLook) {

                    MapStatus ms = new MapStatus.Builder().overlook(90).build();
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
                } else {
                    MapStatus ms = new MapStatus.Builder().overlook(overlookvalue).build();
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
                }
                isOverLook = !isOverLook;
                break;
            case R.id.jia:
                //放大地图
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.jian:
                //缩小地图
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
            case R.id.weizhi:
                //定位
                Log.i("baidumap", "开始定位");
                locationService.start();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (menu.isMenuShowing()) {
                menu.toggle();
                return false;
            }
            CarToolsC.bluetoothCS.sendMessageHandle(Command.EXIT);
            System.exit(0);
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * *****************百度地图******************************
     */
    private LocationService locationService;

    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mLocListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                getLocation(location);
                Log.i("baidumap", sb.toString());
            }
        }

    };

    //定位
    private void getLocation(BDLocation location) {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);


        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        MapStatus ms = new MapStatus.Builder().build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(ms));


        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.weizhi);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);

        // 修改为自定义marker
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.zuobiao);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));

        // 当不需要定位图层时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);
        locationService.stop(); //停止定位服务
    }


    public BroadcastReceiver PowerConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            Log.i("Receiver", "isCharging:"+isCharging+" usbCharge:"+usbCharge+" acCharge:"+acCharge);
//            Toast.makeText(context, status + " isCharging:" + isCharging + " usbCharge:" + usbCharge + " acCharge:" + acCharge, Toast.LENGTH_SHORT).show();
//            if(isCharging &&  GestureActivity.instance==null){
//                Intent intent1=new Intent(context, GestureActivity.class);
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent1);

//            }else if(GestureActivity.instance!=null){
//                 GestureActivity.instance.finish();
//            }

            if(intent.getAction()==Intent.ACTION_POWER_CONNECTED){
                goToGesture();
                powerIsConnected = true;
            }else if(intent.getAction()==Intent.ACTION_POWER_DISCONNECTED){
                if(GestureActivity.instance!=null)
                  GestureActivity.instance.finish();
                powerIsConnected = false;
            }else if(intent.getAction()==Intent.ACTION_BATTERY_CHANGED && isCharging){
                goToGesture();
                powerIsConnected = true;
            }
        }
    };

    public void goToGesture(){
        if(bluetoothIsConnected && powerIsConnected && GestureActivity.instance==null){
            Intent intent1 = new Intent(context, GestureActivity.class);
            context.startActivity(intent1);
            if(alreadyPlayVidio){
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SAVEADDRESS);
                alreadyPlayVidio=false;
            }
        }
    }

}
