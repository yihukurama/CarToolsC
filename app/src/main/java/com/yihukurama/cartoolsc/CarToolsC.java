package com.yihukurama.cartoolsc;

import android.app.Application;

import com.yihukurama.cartoolsc.controler.bluetooth.BluetoothCS;
import com.yihukurama.cartoolsc.controler.handler.BlueHandler;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class CarToolsC extends Application {

    public static BluetoothCS bluetoothCS;
    public static BlueHandler LinkDetectedHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        LinkDetectedHandler = new BlueHandler();
        CarToolsC.bluetoothCS = new BluetoothCS("CC:79:DF:0B:1E:6D",LinkDetectedHandler);
    }
}
