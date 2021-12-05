package com.example.ncrexample.viewmodel;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.ncrexample.Data.DatabaseHandler;
import com.example.ncrexample.R;
import com.example.ncrexample.model.Bluetooth;
import com.example.ncrexample.model.Item;


import java.util.ArrayList;
import java.util.List;

public class BluetoothViewModel extends AndroidViewModel {

    DatabaseHandler databaseHandler;
    public MutableLiveData<ArrayList<Bluetooth>> bluetoothLiveData;
    public ArrayList<Bluetooth> bluetoothArrayList;


    public BluetoothViewModel(@NonNull Application application) {
        super(application);

        databaseHandler = new DatabaseHandler(application);
        bluetoothLiveData = new MutableLiveData<>();
        init();
    }


    public MutableLiveData<ArrayList<Bluetooth>> getBluetoothMutableLiveData() {
        return bluetoothLiveData;
    }

    public void init() {
        populateList();
        bluetoothLiveData.setValue(bluetoothArrayList);
    }

    public void populateList() {
        bluetoothArrayList = new ArrayList<>();
        bluetoothArrayList = databaseHandler.getPreviousDateTime();

    }

}
