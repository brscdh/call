package com.example.call.util;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.call.activity.HomeActivity;
import com.example.call.adapter.adapter_call;
import com.example.call.adapter.adapter_yt;
import com.example.call.model.Call;
import com.example.call.model.Database;

import java.util.ArrayList;
import java.util.List;

public class DataClass {
    public  List<Call> listContact;
    public  List<Call> listStar;
    //public static Context context;
    public int vitri;
    public  adapter_call adapterCall;
    public adapter_yt adapterYt;
    public  Database database ;

    public DataClass() {
        listContact = new ArrayList<>();
        listStar = new ArrayList<>();
    }

    public void cursorContactList(List<Call> callList) {
        listContact.clear();
        listContact.addAll(callList);
        if(adapterCall != null){
            adapterCall.notifyDataSetChanged();
        }
    }

    public void cursorContact(){
        //createtable();
        Cursor cursor = database.select("SELECT * FROM CALL");
        listContact.clear();
        while(cursor.moveToNext()){
            listContact.add(new Call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        adapterCall.notifyDataSetChanged();
    }
    public void cursorStar(){
        Cursor cursor =database.select("SELECT * FROM CALL");
        listContact.clear();
        while(cursor.moveToNext()){
            listStar.add(new Call(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            ));
        }
        adapterCall.notifyDataSetChanged();
    }
//    public static void createtable(){
//        //database = new Database(context, "call", null, 1);
//        database.query("CREATE TABLE IF NOT EXISTS CALL(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "TEN TEXT, SDT TEXT)");
//    }
}
