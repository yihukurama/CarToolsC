package com.yihukurama.cartoolsc.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.controler.bluetooth.BluetoothCS;
import com.yihukurama.cartoolsc.view.activity.secactivity.DuomeitiActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    Button daohan;
    Button diantai;
    Button duomeiti;
    TextView connectText;
    BluetoothCS bcs;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepare();
        initView();
        initData();
    }

    private void initView() {
        daohan = (Button)findViewById(R.id.daohan);
        daohan.setOnClickListener(this);

        diantai = (Button)findViewById(R.id.diantai);
        diantai.setOnClickListener(this);

        connectText = (TextView)findViewById(R.id.connect);
        connectText.setOnClickListener(this);

        duomeiti = (Button)findViewById(R.id.duomeiti);
        duomeiti.setOnClickListener(this);

    }

    private void initData() {
        bcs = new BluetoothCS("1C:87:2C:9D:2A:38",LinkDetectedHandler);


    }

    private void prepare() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        String cmd;
        switch (v.getId()){
            case R.id.connect:
                bcs.startClient();
                break;
            case R.id.daohan:
                cmd = "daohan";
                bcs.sendMessageHandle(cmd);
                break;
            case R.id.diantai:
                cmd = "diantai";
                bcs.sendMessageHandle(cmd);
                break;
            case R.id.duomeiti:
                startActivity(new Intent(this, DuomeitiActivity.class));
                break;

        }
    }

    private Handler LinkDetectedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what==1)//客户端
            {
                String mes = (String)msg.obj;
                message = "Server:"+message+"\n"+mes;
                Log.i("bluetooth", "client" + mes);
                connectText.setText(mes);
                showLongToast(mes);
            }
            else//服务器端
            {
                String remes = (String)msg.obj;
                Log.i("bluetooth","server"+remes);
                message = "Server:"+message+"\n"+remes;
                connectText.setText(remes);
                showLongToast(remes);
            }
        }

    };
}
