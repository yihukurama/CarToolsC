package com.yihukurama.cartoolsc.controler.handler;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.yihukurama.cartoolsc.R;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class BlueHandler extends Handler{
    String message;
    TextView connectText;
    Button lianjieBtn;

    public BlueHandler(String message,TextView connectText,Button lianjieBtn){
        this.message = message;
        this.connectText = connectText;
        this.lianjieBtn = lianjieBtn;
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

    public Button getLianjieBtn() {
        return lianjieBtn;
    }

    public void setLianjieBtn(Button lianjieBtn) {
        this.lianjieBtn = lianjieBtn;
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == 3){//连接上了
            String mes = (String) msg.obj;
            message = "Server:" + message + "\n" + mes;
            Log.i("bluetooth", "client" + mes);
            connectText.setText(mes);
            lianjieBtn.setBackgroundResource(R.mipmap.ae86_yilianjie);
        }else if (msg.what == 1)//客户端
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
