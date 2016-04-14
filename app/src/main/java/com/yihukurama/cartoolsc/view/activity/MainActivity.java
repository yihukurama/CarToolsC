package com.yihukurama.cartoolsc.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yihukurama.cartoolsc.CarToolsC;
import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.model.Command;
import com.yihukurama.cartoolsc.view.activity.secactivity.GestureActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    Button daohan;
    Button diantai;
    Button duomeiti;
    Button tongxun;
    Button shushi;
    TextView connectText;
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

        diantai = (Button)findViewById(R.id.cheliangfuwu);
        diantai.setOnClickListener(this);

        connectText = (TextView)findViewById(R.id.connect);
        connectText.setOnClickListener(this);

        duomeiti = (Button)findViewById(R.id.shoushi);
        duomeiti.setOnClickListener(this);

        shushi = (Button)findViewById(R.id.shezhi);
        shushi.setOnClickListener(this);

        tongxun = (Button)findViewById(R.id.yaokong);
        tongxun.setOnClickListener(this);

    }

    private void initData() {


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connect:

                break;
            case R.id.daohan:

                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPDAOHAN);
                break;
            case R.id.cheliangfuwu:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPDIANTAI);
                break;
            case R.id.shezhi:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPSHUSHI);
                break;
            case R.id.yaokong:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPTONGXUN);
                CarToolsC.bluetoothCS.shutdownClient();
                CarToolsC.bluetoothCS.startClient();
                break;
            case R.id.shoushi:
                CarToolsC.bluetoothCS.sendMessageHandle(Command.SKIPDUOMEITI);
                startActivity(new Intent(this,GestureActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            CarToolsC.bluetoothCS.sendMessageHandle(Command.EXIT);
            System.exit(0);
        }

        return super.onKeyDown(keyCode, event);
    }

}
