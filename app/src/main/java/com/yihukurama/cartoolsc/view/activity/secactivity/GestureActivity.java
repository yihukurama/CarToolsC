package com.yihukurama.cartoolsc.view.activity.secactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihukurama.cartoolsc.CarToolsC;
import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.model.Command;
import com.yihukurama.cartoolsc.view.activity.BaseActivity;
import com.yihukurama.cartoolsc.view.widget.BebasNeueFontTextView;

public class GestureActivity extends BaseActivity implements GestureDetector.OnGestureListener{

    final String TAG = GestureActivity.class.getSimpleName();
    double moveY;
    double moveX;
    double bX,bY,eX,eY;
    int mode;
    GestureDetector gestureDetector;
    TextView fankui;
    String message;
    public static GestureActivity instance;

    ImageView huadong_tips;
    TextView num;
    TextView hint;
    ImageView huadong_show;
    int yinliang_tmp=-1;
    int yinliang_last=-1;
    boolean isFistChangeYinlinag=true;

    long endGestureTime =0;
    boolean isScoll=false;
    boolean isUpOrDown=false;

    Handler mHandler;
    int image_alpha = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_gesture);
        instance = this;
        Log.i(TAG, "手势页面");
        prepare();
        initView();
        initData();

    }

    private void initView() {
        huadong_tips = (ImageView) findViewById(R.id.huadong_tips);
        num = (BebasNeueFontTextView)findViewById(R.id.num);
        hint = (TextView) findViewById(R.id.hint);
        huadong_show = (ImageView) findViewById(R.id.huadong_show);

        fankui = (TextView)findViewById(R.id.fankui);
    }

    private void prepare() {
        context = this;
    }

    private void initData() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "image_alpha " + image_alpha);
                huadong_tips.setImageAlpha(255 - image_alpha);
                huadong_show.setImageAlpha(image_alpha);
                num.setTextColor(Color.argb(image_alpha, 255, 255, 255));
                hint.setTextColor(Color.argb(image_alpha, 171, 176, 190));
                // 刷新视图
//                up.invalidate();

            }
        };

        image_alpha=0;
        mHandler.sendMessage(mHandler.obtainMessage());

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
                endGestureTime = System.currentTimeMillis();
                mode = 1;
                Log.i(TAG,"单点");
                bX = event.getX();
                bY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                eX = event.getX();
                eY = event.getY();
                endGesture();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode += 1;
                Log.i(TAG,"多点"+mode);
                if(mode==3){
                }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
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
        isScoll = true;
        float mOldX = e1.getX();
        float mOldY = e1.getY();
        float x = e2.getX();
        float y = e2.getY();
        Display disp = getWindowManager().getDefaultDisplay();
        int windowHeight = disp.getHeight();

        moveX = mOldX - x;
        moveY = mOldY - y;

        Log.i(TAG, moveX + " " + moveY);

        if (Math.abs(moveX)<Math.abs(moveY) && mode > 0 && Math.abs(moveY)-Math.abs(moveX)>100){
            isUpOrDown = true;

            if(yinliang_tmp == -1){
                yinliang_tmp = yinliang;
                isFistChangeYinlinag=true;
            }else
                isFistChangeYinlinag=false;

            yinliang_last = yinliang;
            yinliang = yinliang_tmp + (int)(moveY/windowHeight*100);
            if(yinliang>100)
                yinliang=100;
            else if(yinliang<0)
                yinliang=0;

            hint.setText("音量");
            num.setText(yinliang + "");

            Log.i(TAG, "滚动" + Command.YINLIANG + ":" + yinliang);
            CarToolsC.bluetoothCS.sendMessageHandle(Command.YINLIANG + ":"+yinliang);
            if(isFistChangeYinlinag){
                if(moveY>0){
                    huadong_show.setImageResource(R.mipmap.up);
                }else if(moveY<0){
                    huadong_show.setImageResource(R.mipmap.down);
                }
            }else {
                if(yinliang>yinliang_last){
                    huadong_show.setImageResource(R.mipmap.up);
                }else if(yinliang<yinliang_last){
                    huadong_show.setImageResource(R.mipmap.down);
                }
            }
            image_alpha=255;
            mHandler.sendMessage(mHandler.obtainMessage());

        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.i(TAG,"mode"+mode);
//        float mOldX = e1.getX();
//        float mOldY = e1.getY();
//        float x = e2.getX();
//        float y =  e2.getY();

//        moveX = x - mOldX;
//        moveY = y - mOldY;

        moveX = eX - bX;
        moveY = eY - bY;
        Log.i(TAG, "onFling："+moveX + " " + moveY);

        if(Math.abs(moveX)<Math.abs(moveY)){//上下滑动
            if(moveY<0){
                Log.i(TAG, "上滑" + mode);
                if(mode == 1){
//                    showShortToast("oneup");
                }else if(mode == 2){
//                    showShortToast("twoup");
                }else{
                }
            }else{
                Log.i(TAG,"下滑"+mode);
                if(mode == 1){

                }else if(mode == 2){
                }else{
//                    CarToolsC.bluetoothCS.sendMessageHandle(Command.THREEDOWN);
                }
            }

        }else if(Math.abs(moveX)>Math.abs(moveY) && !isUpOrDown){//左右滑动
            if(moveX<0){
                Log.i(TAG, "左滑" + mode);
                if(mode >= 1){

//                }else if(mode == 2){
//
//                }else{
                    if(--wendu<16)
                        wendu=16;
                    num.setText(wendu + "");
                    hint.setText("温度");
                    huadong_show.setImageResource(R.mipmap.left);
                    image_alpha=255;
                    mHandler.sendMessage(mHandler.obtainMessage());
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.WENDUJIAN+ ":"+wendu);
                }
            }else{
                Log.i(TAG, "右滑" + mode);
                if(mode >= 1){
//
//                }else if(mode == 2){
//
//                }else{
                    if(++wendu>30)
                        wendu=30;
                    num.setText(wendu + "");
                    hint.setText("温度");
                    huadong_show.setImageResource(R.mipmap.right);
                    image_alpha=255;
                    mHandler.sendMessage(mHandler.obtainMessage());
                    CarToolsC.bluetoothCS.sendMessageHandle(Command.WENDUJIA+ ":"+wendu);
                }
            }

            Vibrator vibrator;// 振动器
            vibrator = (Vibrator) getSystemService(context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }

        isScoll=false;
        isUpOrDown=false;
        fankui.setText(CarToolsC.LinkDetectedHandler.getMessage());
        mode = 0;
        Log.i(TAG,"清除");
        return false;
    }



    /** 手势结束 */
    private void endGesture() {
        isScoll=false;
        yinliang_tmp = -1;
        endGestureTime = System.currentTimeMillis();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(System.currentTimeMillis()-endGestureTime>=2000){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            huadong_tips.setVisibility(View.VISIBLE);
//                            num.setVisibility(View.GONE);
//                            hint.setVisibility(View.GONE);
//                            up.setVisibility(View.GONE);
//                            down.setVisibility(View.GONE);
//                            left.setVisibility(View.GONE);
//                            right.setVisibility(View.GONE);
//                        }

                    image_alpha = 255;
                    while (image_alpha>=5 && !isScoll) {
                        try {
                            image_alpha-=5;
                            Thread.sleep(10 );
                            // 更新Alpha值
                            mHandler.sendMessage(mHandler.obtainMessage());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        // 隐藏
//        CarToolsC.bluetoothCS.sendMessageHandle(Command.ENDGESTURE);
        Log.i(TAG,"结束手势");
    }
}
