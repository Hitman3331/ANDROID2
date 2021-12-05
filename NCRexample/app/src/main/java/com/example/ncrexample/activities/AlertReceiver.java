package com.example.ncrexample.activities;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.ncrexample.NotificationChannels.CHANNEL_2_ID;
import static com.example.ncrexample.NotificationChannels.CHANNEL_ID;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.ncrexample.R;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_2_ID)
                .setContentTitle("Running...")
                .setContentText("Service still running...")
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(2);
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification.build());


    }


}