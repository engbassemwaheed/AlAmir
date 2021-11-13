package com.waheed.bassem.alamir.ui.main_activity;

import com.waheed.bassem.alamir.database.StoreItem;

public interface StoreViewInterface {

    void editStoreItem(StoreItem storeItem);
    void callPhone (String phoneNumber);
    void navigate (String address);

}
