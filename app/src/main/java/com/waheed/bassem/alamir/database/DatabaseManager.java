package com.waheed.bassem.alamir.database;

import android.content.Context;

import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseManager databaseManager;
    private final DatabaseHandler databaseHandler;

    private DatabaseManager(Context context) {
        databaseHandler = DatabaseHandler.getInstance(context);
    }

    public static DatabaseManager getInstance (Context context) {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager(context);
        }

        return databaseManager;
    }


    public boolean addStoreItem(StoreItem storeItem){
        if (storeItem != null) return databaseHandler.addStoreItem(storeItem);
        else return false;
    }

    public boolean updateStoreItem(StoreItem storeItem) {
        if (storeItem != null) return databaseHandler.updateStoreItem(storeItem);
        else return false;
    }


    public boolean deleteStoreItem (String id) {
        if (id != null) return databaseHandler.deleteStoreItem(id);
        else return false;
    }

    public ArrayList<StoreItem> getAllStoreItems() {
        return databaseHandler.getAllStoreItems();
    }

    public ArrayList<StoreItem> searchStoreItems(String searchString) {
        return databaseHandler.searchStoreItems(searchString);
    }

}
