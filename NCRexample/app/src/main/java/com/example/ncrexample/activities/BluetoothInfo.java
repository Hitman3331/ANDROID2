package com.example.ncrexample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SwitchCompat;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ncrexample.Data.DatabaseHandler;
import com.example.ncrexample.R;
import com.example.ncrexample.fragment.DateListFragment;
import com.example.ncrexample.model.Bluetooth;
import com.example.ncrexample.viewmodel.BluetoothViewModel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class BluetoothInfo extends AppCompatActivity implements LifecycleOwner {

    BroadcastReceiver mBroadcastReceiver1;
    TextView showCurrentTime;
    BluetoothAdapter bluetoothAdapter;
    DatabaseHandler db;
    BluetoothViewModel bluetoothViewModel;
    ListView listView;
    SwitchCompat switchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_info);

        db = new DatabaseHandler(this);
        showCurrentTime = findViewById(R.id.showCurrentTime);
       // listView = (ListView) findViewById(R.id.listViewID);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        switchBtn=findViewById(R.id.switchId);
        Calendar calendar = Calendar.getInstance();

        DateListFragment fragment=new DateListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView,fragment).commit();
        Toast.makeText(this, "size "+db.getPreviousDateTime().size(), Toast.LENGTH_SHORT).show();
            Log.d("TAG","size main "+db.getPreviousDateTime().size());
        bluetoothViewModel = new ViewModelProvider(this).get(BluetoothViewModel.class);
        //fragment.saveDateTime();

        //addList();

/*
        bluetoothViewModel = new ViewModelProvider(this).get(BluetoothViewModel.class);
        Observer<ArrayList<Bluetooth>> bluetoothListUpdateObserver = new Observer<ArrayList<Bluetooth>>() {
            @Override
            public void onChanged(ArrayList<Bluetooth> theList) {
                ListAdapter listAdapter = new ArrayAdapter<>(BluetoothInfo.this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        };
        bluetoothViewModel.getBluetoothMutableLiveData().observe(this, bluetoothListUpdateObserver);

*/

        String currentTime = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        showCurrentTime.setText(currentTime);
        checkPhoneState();


        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (!bluetoothAdapter.isEnabled()) {
                        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);}
                }
                else {
                    bluetoothAdapter.disable();
                }
            }
        });


        mBroadcastReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                    switch (state) {
                        case BluetoothAdapter.STATE_OFF:

                            Toast.makeText(context, "Bluetooth is off", Toast.LENGTH_SHORT).show();
                            Log.d("BroadcastActions", "Bluetooth is off");
                            saveDateTime();
                            if(switchBtn.isChecked()) {switchBtn.setChecked(false);}
                            switchBtn.setText("Disabled");
                            break;

                        case BluetoothAdapter.STATE_TURNING_OFF:

                            Toast.makeText(context, "Bluetooth is turning off", Toast.LENGTH_SHORT).show();
                            //Log.d("BroadcastActions", "Bluetooth is turning off");
                            break;

                        case BluetoothAdapter.STATE_ON:

                            Toast.makeText(context, "Bluetooth is turning on", Toast.LENGTH_SHORT).show();
                            Log.d("BroadcastActions", "Bluetooth is on");
                            if(!switchBtn.isChecked()) {switchBtn.setChecked(true);}
                                switchBtn.setText("Enable");
                            break;

                        case BluetoothAdapter.STATE_TURNING_ON:

                            Toast.makeText(context, "Bluetooth is turning on", Toast.LENGTH_SHORT).show();
                            Log.d("BroadcastActions", "Bluetooth is turning on");
                            break;
                    }
                }
            }

        };

        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver1, filter1);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
    }


    public void saveDateTime() {
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        db.addBluetooth(new Bluetooth(mydate));
        bluetoothViewModel.bluetoothLiveData.setValue(db.getPreviousDateTime());

    }

    public void checkPhoneState() {
        if (bluetoothAdapter!=null) {
            if (!bluetoothAdapter.isEnabled()) {
                switchBtn.setChecked(false);
            } else {
                switchBtn.setChecked(true);
                switchBtn.setText("Enable");
            }
        }
    }


    public void addList(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DateListFragment fragment=new DateListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, fragment)
                .setReorderingAllowed(true)
                .commit();
    }


}