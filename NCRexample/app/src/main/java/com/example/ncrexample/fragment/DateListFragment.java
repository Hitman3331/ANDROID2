package com.example.ncrexample.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ncrexample.Data.DatabaseHandler;
import com.example.ncrexample.R;
import com.example.ncrexample.activities.BluetoothInfo;
import com.example.ncrexample.model.Bluetooth;
import com.example.ncrexample.viewmodel.BluetoothViewModel;

import java.util.ArrayList;
import java.util.Calendar;


public class DateListFragment extends Fragment {



    DatabaseHandler db;
    BluetoothViewModel bluetoothViewModel;

   ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db=new DatabaseHandler(requireActivity());

        Toast.makeText(getContext(), "size"+db.getPreviousDateTime().size(), Toast.LENGTH_SHORT).show();

        bluetoothViewModel=new ViewModelProvider(requireActivity()).get(BluetoothViewModel.class);
       // saveDateTime();


        Observer<ArrayList<Bluetooth>> listObserver =new Observer<ArrayList<Bluetooth>>() {
            @Override
            public void onChanged(ArrayList<Bluetooth> theList) {

                ListAdapter adapter=new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(adapter);


            }
        };
        bluetoothViewModel.getBluetoothMutableLiveData().observe(getActivity(),listObserver);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_date_list, container, false);



       listView=view.findViewById(R.id.lstID);

        return view;
    }

    public void saveDateTime() {
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        db.addBluetooth(new Bluetooth(mydate));
        bluetoothViewModel.bluetoothLiveData.setValue(db.getPreviousDateTime());

    }



}