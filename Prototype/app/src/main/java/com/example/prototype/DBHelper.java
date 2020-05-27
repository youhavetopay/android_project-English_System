package com.example.prototype;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Wordbook1.db";

    //단어장 디비
    private static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT) ", DbContract.DbEntry.TABLE_NAME, DbContract.DbEntry._ID,
            DbContract.DbEntry.WORDBOOK_NAME_TITLE, DbContract.DbEntry.WORDBOOK_NAME_SUBTITLE);
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DbContract.DbEntry.TABLE_NAME;

    //단어 디비
    private static final String SQL_CREATE_ENTRIES2 = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,  %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT NOT NULL)",
            DbContract.DbEntry2.TABLE_NAME, DbContract.DbEntry2._ID, DbContract.DbEntry2.WORD_SPELL, DbContract.DbEntry2.WORD_MEAN1, DbContract.DbEntry2.WORD_MEAN2, DbContract.DbEntry2.WORD_MEAN3, DbContract.DbEntry2.WORD_MEAN4,
            DbContract.DbEntry2.WORD_MEAN5, DbContract.DbEntry2.WORDBOOK_ID);
    private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + DbContract.DbEntry2.TABLE_NAME;




    public static DBHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES2);
        onCreate(db);
    }
}
