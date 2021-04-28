package com.rku_18fotca11036.kishanbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperIncome extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "income";
    public static final String TABLE_NAME = "income_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "date";
    public static final String COL_3 = "money";

    public DatabaseHelperIncome(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID integer primary key autoincrement, date text, money text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String date, String money) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, date);
        contentValues.put(COL_3, money);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    public boolean updateData(String id, String money){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_3, money);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[] {id});

    }
}
