package com.example.ncrexample.activities;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.example.ncrexample.R;
import com.example.ncrexample.adapter.InformationAdapter;
import com.example.ncrexample.model.Item;
import com.example.ncrexample.service.ExampleService;
import com.example.ncrexample.viewmodel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    MainViewModel viewModel;
    RecyclerView recyclerView;
    InformationAdapter recyclerViewAdapter;
    public Toolbar toolbar;
    WifiManager wifiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerId);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getUserMutableLiveData().observe(this, userListUpdateObserver);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);


    }


    Observer<ArrayList<Item>> userListUpdateObserver = new Observer<ArrayList<Item>>() {
        @Override
        public void onChanged(ArrayList<Item> userArrayList) {
            recyclerViewAdapter = new InformationAdapter(MainActivity.this, userArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // Inflate the custom overflow menu
        menuInflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about && isInternetConnection()) {
            startActivity(new Intent(MainActivity.this, AboutNCR.class));
        }

        if (item.getItemId() == R.id.exit) {
            serviceAlertDialog();
        }
        if (item.getItemId() == R.id.info) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, ExampleService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Intent serviceIntent = new Intent(this, ExampleService.class);
        //stopService(serviceIntent);

    }

    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //Toast.makeText(MainActivity.this,"Connection is good",Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(MainActivity.this, "Check your Wi-Fi", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public void serviceAlertDialog(){

       new AlertDialog.Builder(this)
                .setTitle("Confirmation message")
                .setMessage("Do you want to stop service?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
                        stopService(serviceIntent);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

    }


}




