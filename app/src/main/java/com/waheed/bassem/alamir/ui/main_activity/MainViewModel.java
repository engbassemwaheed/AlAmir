package com.waheed.bassem.alamir.ui.main_activity;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.waheed.bassem.alamir.database.DatabaseManager;
import com.waheed.bassem.alamir.database.StoreItem;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<StoreItem>> storesMutableLiveData;
    private DatabaseManager databaseManager;

    private boolean firstTime;
    private String searchString;

    public MainViewModel() {
        storesMutableLiveData = new MutableLiveData<>();
        firstTime = true;
    }

    public void init(Context context) {
        databaseManager = DatabaseManager.getInstance(context);
    }

    public MutableLiveData<ArrayList<StoreItem>> getStoresMutableLiveData() {
        firstTime = false;
        return storesMutableLiveData;
    }

    public void getStoreItemsFirstTime() {
        firstTime = false;
        ArrayList<StoreItem> result;
        if (searchString != null && !searchString.isEmpty()) {
            result = databaseManager.searchStoreItems(searchString);
        } else {
            result = databaseManager.getAllStoreItems();
        }
        storesMutableLiveData.setValue(result);
    }

    public void search (String search) {
        searchString = search;
        firstTime = false;
        ArrayList<StoreItem> result = databaseManager.searchStoreItems(search);
        storesMutableLiveData.setValue(result);
    }

    public void getStoreItems() {
        searchString = null;
        firstTime = false;
        ArrayList<StoreItem> result = databaseManager.getAllStoreItems();
        storesMutableLiveData.setValue(result);
    }





}
