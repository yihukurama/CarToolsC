package com.yihukurama.cartoolsc.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

import com.yihukurama.cartoolsc.view.activity.secactivity.GestureActivity;

public class PowerConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Log.i("Receiver", "isCharging:"+isCharging+" usbCharge:"+usbCharge+" acCharge:"+acCharge);
        Toast.makeText(context,status+" isCharging:"+isCharging+" usbCharge:"+usbCharge+" acCharge:"+acCharge,Toast.LENGTH_SHORT).show();
        if(!isCharging && acCharge &&  GestureActivity.instance==null){
            Intent intent1=new Intent(context, GestureActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);

        }else{
            GestureActivity.instance.finish();
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
