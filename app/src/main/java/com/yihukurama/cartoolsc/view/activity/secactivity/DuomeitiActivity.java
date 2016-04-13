package com.yihukurama.cartoolsc.view.activity.secactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.yihukurama.cartoolsc.R;
import com.yihukurama.cartoolsc.view.activity.BaseActivity;

public class DuomeitiActivity extends BaseActivity implements GestureDetector.OnGestureListener{

    final String TAG = DuomeitiActivity.class.getSimpleName();
    double moveY;
    double moveX;
    double bX,bY,eX,eY;
    int mode;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duomeiti);
        Log.i(TAG, "手势页面");
        prepare();
        initData();
    }

    private void prepare() {
        context = this;
    }

    private void initData() {
        gestureDetector = new GestureDetector(this,this);
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
                }else{
                    showShortToast("threeup");
                }
            }else{
                Log.i(TAG,"下滑"+mode);
            }

        }else{//左右滑动
            if(moveX<0){
                Log.i(TAG,"左滑"+mode);
                if(mode == 1){
                    showShortToast("oneleft");
                }else if(mode == 2){
                    showShortToast("twoleft");
                }else{
                    showShortToast("threeleft");
                }
            }else{
                Log.i(TAG,"右滑"+mode);
            }
        }




        mode = 0;
        Log.i(TAG,"清除");
        return false;
    }
}
