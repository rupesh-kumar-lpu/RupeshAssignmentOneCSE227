package com.example.rupeshkumarassignmentone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyBudgetDataBase extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public MyBudgetDataBase(Context context) {
        super(context, "Budget", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Budget(Id INTEGER PRIMARY KEY AUTOINCREMENT,Type TEXT,Value INTEGER,Note Text,Date Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Budget");
        onCreate(db);


    }

    long insertRegisterDataInTable(String type,String val,String note,String date){
        ContentValues contentValues=new ContentValues();
        contentValues.put("Type",type);
        contentValues.put("Value",val);
        contentValues.put("Note",note);
        contentValues.put("Date",date);


        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        long id=sqLiteDatabase.insert("Budget",null,contentValues);
        return  id;
    }



    public Cursor getalldata(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from Budget",null);
        return res;
    }


    public Cursor getalldatarow(String em){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();


        Cursor  cursor = sqLiteDatabase.rawQuery("SELECT * FROM Budget WHERE Type = ? LIMIT 1",
                new String[]{em});

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }







    public Boolean checkEmailAlready(String email) {

        // array of columns to fetch
        String[] columns = {
                "Type"
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = "Type" + " = ?";

        // selection arguments
        String[] selectionArgs = {email};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query("Budget", //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }



    public void clearall(int id) {

        ContentValues cvv = new ContentValues();
        cvv.putNull("Type");
        cvv.putNull("Value");
        cvv.putNull("Note");
        cvv.putNull("Date");
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.update("Budget",cvv,"Id="+id, null);
    }

    public void cleartable(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        sqLiteDatabase.execSQL("delete from "+ "Budget");
    }

    public Cursor getalldatarowfromdt(String emd) {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();


        Cursor  cursor = sqLiteDatabase.rawQuery("SELECT * FROM Budget WHERE Date = ?",
                new String[]{emd});

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }


}