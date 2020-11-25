package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

/**
 * Created by Alex on 8/15/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context,Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACT_TABLE="CREATE TABLE " + Util.TABLE_NAME + "(" +Util.KEY_ID  + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT," + Util.KEY_PHONE_NUMBER + " TEXT" + ")";

        db.execSQL(CREATE_CONTACT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {




        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME);

        //create table again

        onCreate(db);




    }


    public void addContact(Contact contact){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues value=new ContentValues();

        value.put(Util.KEY_NAME,contact.getName());
        value.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());
        db.insert(Util.TABLE_NAME,null,value);
        db.close();

    }



    public Contact getContact(int id){

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Util.TABLE_NAME,new String[]{Util.KEY_ID,Util.KEY_NAME,Util.KEY_PHONE_NUMBER},Util.KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);


        if(cursor!=null)
            cursor.moveToFirst();
        Contact contact=new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));

        return contact;

    }



    public List<Contact> getAllContacts(){

        SQLiteDatabase db=this.getReadableDatabase();

        List<Contact> conntactList=new ArrayList<>();

        String selectAll="SELECT * FROM "+Util.TABLE_NAME;

        Cursor cursor=db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){

            do {


                Contact contact=new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

            }while (cursor.moveToNext());


        }

        return  conntactList;


    }



}
