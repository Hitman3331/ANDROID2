package com.example.ncrexample.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ncrexample.R;
import com.example.ncrexample.model.Item;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    MutableLiveData<ArrayList<Item>> userLiveData;
    ArrayList<Item> userArrayList;

    public MainViewModel() {
        userLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Item>> getUserMutableLiveData() {
        return userLiveData;
    }

    public void init() {
        populateList();
        userLiveData.setValue(userArrayList);
    }

    public void populateList() {


        userArrayList = new ArrayList<>();
        userArrayList.add(new Item("OSInfo", R.drawable.ic_phone));
        userArrayList.add(new Item("BatteryInfo", R.drawable.ic_battery));
        userArrayList.add(new Item("BluetoothInfo", R.drawable.ic_bluetooth));


    }


}
