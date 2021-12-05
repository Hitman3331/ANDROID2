package com.example.ncrexample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ncrexample.R;

public class BatteryInfo extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_info);

        result=findViewById(R.id.result);
        loadBatterySection();

    }

    private void loadBatterySection() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryInfoReceiver, intentFilter);
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateBatteryData(intent);
        }
    };

    private void updateBatteryData(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
        if(present){

            StringBuilder batteryinfo=new StringBuilder();
            int health=intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            batteryinfo.append("Health : ".toUpperCase()+health).append("\n"+"\n");

            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);

            if(level !=-1 && scale !=-1){
                int batteryPct=(int) ((level / (float) scale) *100f);
                batteryinfo.append("Battery Pct : ".toUpperCase()+batteryPct).append("\n"+"\n");
            }

            int plugged=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            batteryinfo.append("Plugged : ".toUpperCase()+plugged).append("\n"+"\n");

            int status=intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            batteryinfo.append("Charging status : ".toUpperCase()+status).append("\n"+"\n");

            if(intent.getExtras()!=null){
                String techology=intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
                batteryinfo.append("Technology : ".toUpperCase()+techology).append("\n"+"\n");
            }

            int temperature=intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            Log.d("TAG","temp"+temperature);
            if (temperature > 0) {
                batteryinfo.append("Temperature : ".toUpperCase()+((float) temperature) / 10f).append("°C\n"+"\n");
            }

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            batteryinfo.append("Voltage: ".toUpperCase()+voltage).append(" mV\n").append("\n");
            long capacity = getBatteryCapacity();
            batteryinfo.append("Capacity: ".toUpperCase()+capacity).append(" mAh\n").append("\n");
            result.setText(batteryinfo);
        }

            else {Toast.makeText(this, "No Battery present", Toast.LENGTH_SHORT).show();}
    }


    public long getBatteryCapacity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            BatteryManager mBatteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
            Long chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            Long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            if (chargeCounter != null && capacity != null) {
                long value = (long) (((float) chargeCounter / (float) capacity) * 100f);
                return value;
            }
        }

        return 0;
    }

}