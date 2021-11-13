package com.waheed.bassem.alamir.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static com.waheed.bassem.alamir.database.Constants.*;
import static com.waheed.bassem.alamir.database.Contract.*;



class DatabaseHandler {

    private static final String TAG = "DatabaseHandler";

    private static DatabaseHandler databaseHandler;
    private final DatabaseHelper databaseHelper;

    private DatabaseHandler(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    static DatabaseHandler getInstance(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }

        return databaseHandler;
    }

    boolean addStoreItem(StoreItem storeItem) {

        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();

        writableDatabase.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(StoreEntry.COLUMN_NAME, storeItem.getName());
            contentValues.put(StoreEntry.COLUMN_CITY, storeItem.getCity());
            contentValues.put(StoreEntry.COLUMN_AREA, storeItem.getArea());
            contentValues.put(StoreEntry.COLUMN_IMAGE, storeItem.getImageBase64());
            contentValues.put(StoreEntry.COLUMN_PHONE_NUMBER, storeItem.getPhone());
            contentValues.put(StoreEntry.COLUMN_ID, storeItem.getId());

            writableDatabase.insert(StoreEntry.TABLE_NAME, null, contentValues);

            writableDatabase.setTransactionSuccessful();

            writableDatabase.endTransaction();

            return true;
        } catch (Exception ex) {
            Log.e(TAG, "exception: " + ex.getMessage());
            writableDatabase.endTransaction();
            return false;
        }
    }

    public boolean deleteStoreItem(String id) {
        try {
            SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();

            String selection = StoreEntry.COLUMN_ID + SQLiteConstants.EQUALS_QUERY_MARK;

            String[] selectionArgs = {id};

            writableDatabase.delete(StoreEntry.TABLE_NAME, selection, selectionArgs);
            writableDatabase.close();

            return true;
        } catch (Exception ex) {
            Log.e(TAG, "exception: " + ex.getMessage());
            return false;
        }
    }

    boolean updateStoreItem(StoreItem storeItem) {
        try {
            SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(StoreEntry.COLUMN_NAME, storeItem.getName());
            contentValues.put(StoreEntry.COLUMN_CITY, storeItem.getCity());
            contentValues.put(StoreEntry.COLUMN_AREA, storeItem.getArea());
            contentValues.put(StoreEntry.COLUMN_IMAGE, storeItem.getImageBase64());
            contentValues.put(StoreEntry.COLUMN_PHONE_NUMBER, storeItem.getPhone());
            contentValues.put(StoreEntry.COLUMN_ID, storeItem.getId());

            String selection = StoreEntry.COLUMN_ID + SQLiteConstants.EQUALS_QUERY_MARK;
            String[] selectionArgs = {storeItem.getId()};

            writableDatabase.update(StoreEntry.TABLE_NAME, contentValues, selection, selectionArgs);

            return true;
        } catch (Exception ex) {
            Log.e(TAG, "exception: " + ex.getMessage());
            return false;
        }
    }

    ArrayList<StoreItem> getAllStoreItems() {
        return searchStoreItems(null);
    }

    ArrayList<StoreItem> searchStoreItems(String searchString) {

        ArrayList<StoreItem> result = new ArrayList<>();

        String selection = null;
        String[] selectionArgs = null;

        if (searchString != null && !searchString.isEmpty()) {
            selection = StoreEntry.COLUMN_NAME + SQLiteConstants.LIKE_QUERY_MARK;
            selectionArgs = new String[]{searchString + SQLiteConstants.PERCENTAGE_SIGN};
        }

        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        String[] projection = {
                StoreEntry.COLUMN_NAME,
                StoreEntry.COLUMN_PHONE_NUMBER,
                StoreEntry.COLUMN_CITY,
                StoreEntry.COLUMN_AREA,
                StoreEntry.COLUMN_IMAGE,
                StoreEntry.COLUMN_ID};


        Cursor cursor = readableDatabase.query(
                StoreEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int count = cursor.getCount();

        if (count > 0) {
            while (cursor.moveToNext()) {

                int idColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_ID);
                int nameColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_NAME);
                int cityColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_CITY);
                int areaColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_AREA);
                int imageColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_IMAGE);
                int phoneNumberColumnIndex = cursor.getColumnIndex(StoreEntry.COLUMN_PHONE_NUMBER);

                String name = cursor.getString(nameColumnIndex);
                String phoneNumber = cursor.getString(phoneNumberColumnIndex);
                String image = cursor.getString(imageColumnIndex);
                String city = cursor.getString(cityColumnIndex);
                String area = cursor.getString(areaColumnIndex);
                String id = cursor.getString(idColumnIndex);

                result.add(new StoreItem(id, name, city, area, phoneNumber, image));
            }
            cursor.close();

        } else {
            cursor.close();
        }

        return result;
    }
}
