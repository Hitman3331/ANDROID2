package com.example.alex.hangdroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    String points;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);


        Intent intent=getIntent();

      points= intent.getStringExtra("SHOW_POINTS");


        TextView tv=findViewById(R.id.textView5);

        tv.setText(points);


    }




    public void saveScore(View view){


         EditText nameEdit=(EditText) findViewById(R.id.editText2);


         String name=nameEdit.getText().toString();

        SharedPreferences preferences=getSharedPreferences("MYPREFERENCES", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=preferences.edit();

        String previousScores=preferences.getString("SCORES","");//prvi krug ce samo prazan string biti upisan u promenljivu previous,a tek onda prvi put kljucu scores se dodaje value prvi put
        //kroz editor put,i onda opet kada proba da izvuce preko getstring value iz kljuca scores imacemo dodeljeni value koji je preko editora upisan,a onda opet dole kad se dodje novo ime koje dodeljujemo
        //plus to prvo koje je dodeljenu previousu,znaci prvo se startije po defaultu sa praznim stringom da se dodeli previousu i onda se radi prvi upis + prazan string i posle toga svaki
        //novi upis bice + prethodno iupisano ime

        editor.putString("SCORES",name +" " + points + " POINTS \n"+previousScores);

        editor.commit();

        finish();

    }






}
