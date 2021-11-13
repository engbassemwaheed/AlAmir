package com.waheed.bassem.alamir.ui.edit_activity;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import com.waheed.bassem.alamir.database.DataValidator;
import com.waheed.bassem.alamir.database.DatabaseManager;
import com.waheed.bassem.alamir.database.StoreItem;

public class EditViewModel extends ViewModel {

    private DatabaseManager databaseManager;
    private DataValidator dataValidator;
    private EditValidationInterface editValidationInterface;

    private Bitmap storeImage = null;
    private String storeName = "";
    private String storeCity = "";
    private String storeArea = "";
    private String storePhone = "";
    private String storeId = null;

    public EditViewModel() {
    }

    public void init(Context context, EditValidationInterface editValidationInterface) {
        databaseManager = DatabaseManager.getInstance(context);
        this.editValidationInterface = editValidationInterface;
        dataValidator = new DataValidator();
    }

    public boolean addToDatabase() {
        boolean result = validate();
        if (!result) return result;

        StoreItem storeItem = new StoreItem();
        storeItem.setName(storeName);
        storeItem.setArea(storeArea);
        storeItem.setCity(storeCity);
        storeItem.setPhone(storePhone);
        storeItem.setImageBase64(storeImage);

        return databaseManager.addStoreItem(storeItem);
    }

    public boolean updateDatabase() {
        boolean result = validate();
        if (!result) return result;

        if (storeId == null) return false;

        StoreItem storeItem = new StoreItem();
        storeItem.setId(storeId);
        storeItem.setName(storeName);
        storeItem.setArea(storeArea);
        storeItem.setCity(storeCity);
        storeItem.setPhone(storePhone);
        storeItem.setImageBase64(storeImage);

        return databaseManager.updateStoreItem(storeItem);
    }

    public boolean deleteFromDatabase() {
        if (storeId == null) return false;

        return databaseManager.deleteStoreItem(storeId);
    }

    private boolean validate() {
        boolean errors;

        errors = dataValidator.isValidImage(storeImage);
        editValidationInterface.handleImageError(errors);
        if (!errors) return errors;

        errors = dataValidator.isValidName(storeName);
        editValidationInterface.handleNameError(errors);
        if (!errors) return errors;

        errors = dataValidator.isValidPlace(storeCity);
        editValidationInterface.handleCityError(errors);
        if (!errors) return errors;

        errors = dataValidator.isValidPlace(storeArea);
        editValidationInterface.handleAreaError(errors);
        if (!errors) return errors;

        errors = dataValidator.isValidPhoneNumber(storePhone);
        editValidationInterface.handlePhoneError(errors);
        if (!errors) return errors;

        return errors;
    }

    public Bitmap getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(Bitmap storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
        editValidationInterface.handleNameError(dataValidator.isValidName(storeName));
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
        editValidationInterface.handleCityError(dataValidator.isValidPlace(storeCity));
    }

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
        editValidationInterface.handleAreaError(dataValidator.isValidPlace(storeArea));
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
        editValidationInterface.handlePhoneError(dataValidator.isValidPhoneNumber(storePhone));
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
