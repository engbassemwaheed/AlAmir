package com.waheed.bassem.alamir.database;

import android.graphics.Bitmap;
import android.telephony.PhoneNumberUtils;

public class DataValidator {

    private static final int PHONE_LENGTH = 11; //without international code

    public DataValidator() {
    }

    public boolean isValidImage(Bitmap image) {
        return image != null;
    }

    //name should not contain any numbers and shouldn't be empty
    public boolean isValidName(String name) {
        return name != null && !name.isEmpty() && !name.matches(".*\\d.*");
    }

    //place shouldn't be empty
    public boolean isValidPlace(String place) {
        return place != null && !place.isEmpty();
    }

    //should be of length of PHONE_LENGTH of numbers and shouldn't be empty
    public boolean isValidPhoneNumber(String number) {
        return number !=null && !number.isEmpty() && number.length() == PHONE_LENGTH;
    }


}
