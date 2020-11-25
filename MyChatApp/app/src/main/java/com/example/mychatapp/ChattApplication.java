package com.example.mychatapp;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseObject;

import model.Message;

/**
 * Created by paulodichone on 4/10/15.
 */
public class ChattApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("OAJRt2DyIWBzJaFJtE0W89m3SXsWfOtO1tQ4pgon")
                .clientKey("l2Ugqz8k9FC31C1kueYHoWgVe32LFPA0wxMlLn7U")
                .server("https://parseapi.back4app.com")
                .build());
    }
}
