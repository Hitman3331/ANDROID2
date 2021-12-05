package com.example.ncrexample.Data;

import static com.example.ncrexample.Utils.Util.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ncrexample.Utils.Util;
import com.example.ncrexample.model.Bluetooth;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BLUETOOTH_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.LAST_TIME_AVAILABLE + " TEXT" + ")";
        db.execSQL(CREATE_BLUETOOTH_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public void addBluetooth(Bluetooth bluetooth) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.LAST_TIME_AVAILABLE, bluetooth.getLastDate());
        db.insert(TABLE_NAME, null, value);
        db.close();

    }


    public ArrayList<Bluetooth> getPreviousDateTime() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Bluetooth> bluetoothList = new ArrayList<>();
        String selectAll = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
        if (cursor.moveToFirst()) {

            do {
                Bluetooth bluetooth = new Bluetooth();
                bluetooth.setId(Integer.parseInt(cursor.getString(0)));
                bluetooth.setLastDate(cursor.getString(1));
                bluetoothList.add(bluetooth);
            } while (cursor.moveToNext());

        }
        return bluetoothList;
    }

}
