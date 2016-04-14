package com.yihukurama.cartoolsc.view.activity.secactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.yihukurama.cartoolsc.CarToolsC;
import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.model.Command;
import com.yihukurama.cartoolsc.view.activity.BaseActivity;

public class GestureActivity extends BaseActivity implements GestureDetector.OnGestureListener{

    final String TAG = GestureActivity.class.getSimpleName();
    double moveY;
    double moveX;
    double bX,bY,eX,eY;
    int mode;
    GestureDetector gestureDetector;
    TextView fankui;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        Log.i(TAG, "手势页面");
        prepare();
        initView();
        initData();
    }

    private void initView() {
        fankui = (TextView)findViewById(R.id.fankui);
    }

    private void prepare() {
        context = this;
    }

    private void initData() {

        gestureDetector = new GestureDetector(this,this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CarToolsC.LinkDetectedHandler.setConnectText(fankui);
        CarToolsC.LinkDetectedHandler.setMessage(message);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = 1;
                bX = event.getX();
                bY = event.getY();
                Log.i(TAG,"单点");
                break;
            case MotionEvent.ACTION_UP:
                eX = event.getX();
                eY = event.getY();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode += 1;
                Log.i(TAG,"多点"+mode);
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指滑动事件
                if (mode == 1) {
                    // 是一个手指拖动

                } else if (mode == 2) {
                    // 两个手指滑动

                }else if(mode == 3){

                }
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG,"mode"+mode);

        moveX = eX - bX;
        moveY = eY - bY;

        if(Math.abs(moveX)<Math.abs(moveY) && Math.abs(moveY)>300){//上下滑动
            if(moveY<0){
                Log.i(TAG, "上滑" + mode);
                if(mode == 1){
                    showShortToast("oneup");
                }else if(mode == 2){
                    showShortToast("twoup");
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.TWOUP);
                }else{
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.THREEUP);
                    showShortToast("threeup");
                }
            }else{
                Log.i(TAG,"下滑"+mode);
                if(mode == 1){

                }else if(mode == 2){
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.TWODOWN);
                }else{
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.THREEDOWN);
                }
            }

        }else{//左右滑动
            if(moveX<0){
                Log.i(TAG,"左滑"+mode);
                if(mode == 1){

                }else if(mode == 2){
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.TWOLEFT);
                }else{

                }
            }else{
                Log.i(TAG,"右滑"+mode);
                if(mode == 1){

                }else if(mode == 2){
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.TWORIGHT);
                }else{

                }
            }
        }



        fankui.setText(CarToolsC.LinkDetectedHandler.getMessage());
        mode = 0;
        Log.i(TAG,"清除");
        return false;
    }

}
