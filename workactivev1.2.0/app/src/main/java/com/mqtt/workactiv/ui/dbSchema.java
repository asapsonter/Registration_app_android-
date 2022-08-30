package com.mqtt.workactiv.ui;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class dbSchema extends SQLiteOpenHelper {

    // Create Database
    public static final String dbName = "SycraDB.db";


    // Create Table LED
    public static final String tblLedConfig = "LedConfig";
    public static final String tblPirConfig = "PirConfig";
    public static final String tblRadarConfig = "RadarConfig";
    public static String TableName = "";
    // Create Columns
    public static final String UUID = "LedConfig";
    public static final String LedName = "LedName";
    public static final String UpdateTime = "time";
    public static final String Elm0 = "Elm0";
    public static final String Elm1 = "Elm1";
    public static final String Elm2 = "Elm2";
    public static final String Elm3 = "Elm3";
    public static final String Elm4 = "Elm4";
    public static final String Elm5 = "Elm5";
    public static final String Elm6 = "Elm6";

    public dbSchema(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+tblLedConfig+
                " (id integer primary key, UUID text, LedName text, UpdateTime text, Elm0 text, Elm1 text, Elm2 text, Elm3 text, Elm4 text, Elm5 text, Elm6 text)");
        sqLiteDatabase.execSQL("create table "+tblPirConfig+
                " (id integer primary key, UUID text, LedName text, UpdateTime text, Elm0 text, Elm1 text, Elm2 text, Elm3 text, Elm4 text, Elm5 text, Elm6 text)");
        sqLiteDatabase.execSQL("create table "+tblRadarConfig+
                " (id integer primary key, UUID text, LedName text, UpdateTime text, Elm0 text, Elm1 text, Elm2 text, Elm3 text, Elm4 text, Elm5 text, Elm6 text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tblLedConfig);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tblPirConfig);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tblRadarConfig);
        onCreate(sqLiteDatabase);
    }

    // Insert data to Database
    public boolean insertData (String UUID, String Name, String Elm, String UpdateTime, String jsonobj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String elm = "Elm"+Elm;
        contentValues.put("id", (byte[]) null);
        contentValues.put("UUID",UUID);
        contentValues.put("LedName",Name);
        contentValues.put("UpdateTime",UpdateTime);
        contentValues.put(elm,jsonobj);
        db.insert(tableCheck(Name), null,contentValues);
        tableCheck(Name);
        db.close();
        return true;
    }

    // Update the Database
    public boolean updateData(String UUID, String Name, String Elm, String UpdateTime, String jsonobj){
        String elm = "Elm"+Elm;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(elm,jsonobj); //These Fields will be your String values of actual column names
        //db.update(tblLedConfig, cv, "UUID="+UUID, null);
        String[] stv = new String[]{UUID};
        Log.d("=============================================", stv.toString());
        db.update(tableCheck(Name), cv, "UUID = ?", new String[]{UUID});
        db.close();
        return true;
    }

    // Check table Name the Database
    public String tableCheck(String tblName){
        if(tblName.contains("LED")){
            TableName = "LedConfig";
            Log.d("Table Name IS 000001 :: ", tblName);
            return tblLedConfig;
        }
        else if(tblName.contains("PIR")){
            TableName = "PirConfig";
            Log.d("Table Name IS 000002 :: ", tblName);
            return tblPirConfig;
        }
        else if(tblName.contains("RADAR")){
            TableName = "RadarConfig";
            Log.d("Table Name IS 000003 :: ", tblName);
            return TableName;
        }
        else{
            Log.d("Table Name IS 000004 :: ", tblName);
            return null;
        }
    }

    // Read data from Database
    @SuppressLint("Range")
    public ArrayList<dbModel> getData(){
        ArrayList<dbModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from LedConfig",null);
        res.moveToFirst();
        String abc;
        while (res.isAfterLast() == false){
            /*
            dbModel model = new dbModel();
            //model.setUUID(res.getString(res.getColumnIndex(UUID)));
            model.setLedName(res.getString(res.getColumnIndex(LedName)));
            //model.setUpdateTime(res.getString(res.getColumnIndex(UpdateTime)));
            model.setElm0(res.getString(res.getColumnIndex(Elm0)));
            model.setElm0(res.getString(res.getColumnIndex(Elm1)));
            model.setElm0(res.getString(res.getColumnIndex(Elm2)));
            model.setElm0(res.getString(res.getColumnIndex(Elm3)));
            model.setElm0(res.getString(res.getColumnIndex(Elm4)));
            model.setElm0(res.getString(res.getColumnIndex(Elm5)));
            model.setElm0(res.getString(res.getColumnIndex(Elm6)));
            */
            abc = res.getString(res.getColumnIndex(Elm0));
            //arrayList.add(model);
            res.moveToNext();
            Log.d("hsadjkhsdfkjhsdkfsdssssssssssssssssssssssss", abc);
        }
        //Log.d("hsadjkhsdfkjhsdkfsd", arrayList);
        return arrayList;
    }

    // Check UUID Exists in DB

    public boolean chkUUID(String UUIDws) {
        String TableName = "LedConfig";
        String ud = "UUID";
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from LedConfig where UUID =" + "\""+ UUIDws+ "\"";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
/*
    // Read OnOffModelLights
    @SuppressLint("Range")
    public ArrayList<String> ReadAllLightsOnOffModel() throws JSONException {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select Elm0 from LedConfig",null);
        res.moveToFirst();
        String abc;
        String a = null;
        while (res.isAfterLast() == false){
            abc = res.getString(res.getColumnIndex("Elm0"));

            //arrayList.add(model);
            res.moveToNext();
            JSONObject jsonObject = new JSONObject(abc);
            //Log.d("========================================================================================================================", );

            jsonObject = jsonObject.getJSONObject("model");
            jsonObject = jsonObject.getJSONArray([0]);
            arrayList.add(jsonObject.getString("model"));
        }
        //Log.d("========================================================================================================================", a);
        Log.d("hsadjkhsd============================== fkjhsdkfsd", arrayList.toString());
        return arrayList;
    }
*/
}
