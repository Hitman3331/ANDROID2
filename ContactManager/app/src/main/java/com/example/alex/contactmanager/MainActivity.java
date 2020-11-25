package com.example.alex.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db=new DatabaseHandler(this);


        Log.d("TEST ","OVO JE TEST");

        db.addContact(new Contact("alex","456389"));
        db.addContact(new Contact("rock","754336"));
        db.addContact(new Contact("sasa","754356"));


        List<Contact> contactList=db.getAllContacts();


        for(Contact c:contactList){

            String log= "ID" + c.getId() + "Name "+c.getName() + "Phone "+c.getPhoneNumber();

            Log.d("Name: ",log);

        }




    }
}
