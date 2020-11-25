package com.example.moviesjson.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {

        sharedPreferences=activity.getPreferences(activity.MODE_PRIVATE);


    }

    public void setSearch(String movie_search){sharedPreferences.edit().putString("search",movie_search).commit();}


    public String getSearch(){return sharedPreferences.getString("search","Superman");}

}
