package com.yihukurama.cartoolsc;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.yihukurama.cartoolsc.controler.bluetooth.BluetoothCS;
import com.yihukurama.cartoolsc.controler.handler.BlueHandler;
import com.yihukurama.cartoolsc.controler.sdk.baidu.Location.service.LocationService;
import com.yihukurama.cartoolsc.controler.sdk.baidu.Location.service.WriteLog;


/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class CarToolsC extends Application {

    public static BluetoothCS bluetoothCS;
    public static BlueHandler LinkDetectedHandler;
    public LocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        LinkDetectedHandler = new BlueHandler();
        CarToolsC.bluetoothCS = new BluetoothCS("CC:79:DF:0B:1E:6D",LinkDetectedHandler);
        //CC:79:DF:0B:1E:6D平板
        //C0:EE:FB:46:90:33手机
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());
    }
}
