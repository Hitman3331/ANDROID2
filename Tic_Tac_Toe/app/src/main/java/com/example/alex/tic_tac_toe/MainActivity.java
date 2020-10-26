package com.example.alex.tic_tac_toe;

import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


setContentView(R.layout.activity_main);
//setTitle(getApplicationContext().toString());

    }


    public void showGame(View v){

        setContentView(R.layout.game_paly);

        GridView gv=(GridView) findViewById(R.id.gridview);

        ImageButtonAdapter iba=new ImageButtonAdapter(this);
        gv.setAdapter(iba);




        Log.d("MYLOG ", "this is grid"+gv );


    }



}
