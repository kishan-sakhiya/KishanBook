package com.rku_18fotca11036.kishanbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.rku_18fotca11036.kishanbook.Fragment.TeamFragment;

public class DatabaseHelperTeam extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "team";
    public static final String TABLE_NAME = "team_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "OFName";
    public static final String COL_3 = "OLName";

    public DatabaseHelperTeam(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID integer primary key autoincrement, OFNAME text, OLNAME text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String ofname, String olname) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, ofname);
        contentValues.put(COL_3, olname);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    public boolean updateDate(String id, String ofname, String olname) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, ofname);
        contentValues.put(COL_3, olname);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       return sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[] {id});

    }
}
