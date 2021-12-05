package com.example.ncrexample.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ncrexample.activities.BatteryInfo;
import com.example.ncrexample.activities.BluetoothInfo;
import com.example.ncrexample.activities.DeviceInfo;
import com.example.ncrexample.R;
import com.example.ncrexample.model.Item;

import java.util.ArrayList;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {

    Activity context;
    ArrayList<Item> userArrayList;

    public InformationAdapter(Activity context, ArrayList<Item> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(rootView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = userArrayList.get(position);
        holder.txtView_title.setText(item.getTitle());
        holder.imgView_icon.setImageResource(item.getImgIcon());

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView_icon;
        TextView txtView_title;


        public ViewHolder(@NonNull View itemView, Activity context) {
            super(itemView);
            imgView_icon = itemView.findViewById(R.id.imgView_icon);
            txtView_title = itemView.findViewById(R.id.txtView_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Item item = userArrayList.get(getAdapterPosition());
                    int position = getAdapterPosition();

                    switch (position) {
                        case 0:
                            context.startActivity(new Intent(context, DeviceInfo.class));
                            break;

                        case 1:
                            context.startActivity(new Intent(context, BatteryInfo.class));
                            break;

                        case 2:
                            context.startActivity(new Intent(context, BluetoothInfo.class));
                            break;

                    }


                }
            });

        }


    }
}