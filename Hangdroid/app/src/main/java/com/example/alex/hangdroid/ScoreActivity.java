package com.example.alex.hangdroid;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        SharedPreferences preferences=getSharedPreferences("MYPREFERENCES",MODE_PRIVATE);

        String scores=preferences.getString("SCORES","NO SCORES");//ucitava key scores ako postoji,ako ne postoji prikazuje no scores,ako je preko editora pre toga za taj kljuc setovana vrednost onda ce
        //tu vrednost prikazati umesto no scores

        TextView textViewScores=(TextView) findViewById(R.id.textViewScore);


        textViewScores.setText(scores);




    }
}
