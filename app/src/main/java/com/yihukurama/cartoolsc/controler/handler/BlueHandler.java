package com.yihukurama.cartoolsc.controler.handler;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class BlueHandler extends Handler{
    String message;
    TextView connectText;

    public BlueHandler(String message,TextView connectText){
        this.message = message;
        this.connectText = connectText;
    }

    public BlueHandler() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TextView getConnectText() {
        return connectText;
    }

    public void setConnectText(TextView connectText) {
        this.connectText = connectText;
    }

    @Override
    public void handleMessage(Message msg) {

        if (msg.what == 1)//客户端
        {
            String mes = (String) msg.obj;
            message = "Server:" + message + "\n" + mes;
            Log.i("bluetooth", "client" + mes);
            connectText.setText(mes);
        } else//服务器端
        {
            String remes = (String) msg.obj;
            Log.i("bluetooth", "server" + remes);
            message = "Server:" + message + "\n" + remes;
            connectText.setText(remes);
        }
    }
}
