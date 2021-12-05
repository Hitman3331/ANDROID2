package com.example.ncrexample.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.ncrexample.BuildConfig;
import com.example.ncrexample.R;



public class DeviceInfo extends AppCompatActivity {

    TextView osRes,verName,verCode,apiLevel,deviceName,model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        osRes=findViewById(R.id.OSversionResult);
        verName=findViewById(R.id.appVersionNameRes);
        verCode=findViewById(R.id.appVersionCodeRes);
        apiLevel=findViewById(R.id.apiLevelRes);
        deviceName=findViewById(R.id.deviceNameRes);
        model=findViewById(R.id.modelRes);

        String os_ver="Android "+Build.VERSION.RELEASE;
        String version_name= BuildConfig.VERSION_NAME;
        String version_code= String.valueOf(BuildConfig.VERSION_CODE);
        int api_level=Build.VERSION.SDK_INT;
        String device_name=Build.DEVICE;
        String model_name=Build.MODEL;


        osRes.setText(os_ver);
        verName.setText(version_name);
        verCode.setText(version_code);
        apiLevel.setText(String.valueOf(api_level));
        deviceName.setText(device_name);
        model.setText(model_name);





    }



}