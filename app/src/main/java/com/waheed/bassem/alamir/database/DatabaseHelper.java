package com.waheed.bassem.alamir.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.waheed.bassem.alamir.database.Constants.SQLiteConstants.*;
import static com.waheed.bassem.alamir.database.Contract.*;


class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    private static final String SQL_CREATE_TABLE = CREATE_TABLE + StoreEntry.TABLE_NAME + OPENED_BRACKET
            + StoreEntry._ID + SPACE + INTEGER + SPACE + AUTO_INCREMENT + SPACE + PRIMARY_KEY + COMMA
            + StoreEntry.COLUMN_AREA + SPACE + TEXT + SPACE + NOT_NULL + COMMA
            + StoreEntry.COLUMN_CITY + SPACE + TEXT + SPACE + NOT_NULL + COMMA
            + StoreEntry.COLUMN_IMAGE + SPACE + TEXT + SPACE + NOT_NULL + COMMA
            + StoreEntry.COLUMN_NAME + SPACE + TEXT + SPACE + NOT_NULL + COMMA
            + StoreEntry.COLUMN_ID + SPACE + TEXT + SPACE + NOT_NULL + COMMA
            + StoreEntry.COLUMN_PHONE_NUMBER + SPACE + TEXT + SPACE + NOT_NULL + CLOSED_BRACKET;

    private DatabaseHelper(Context context) {
        super(context, Constants.STORE_DATABASE_NAME, null, Constants.STORE_DATABASE_VERSION);
    }

    static DatabaseHelper getInstance (Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }

        return databaseHelper;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: handle database version update
    }
}
