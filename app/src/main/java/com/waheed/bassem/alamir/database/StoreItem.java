package com.waheed.bassem.alamir.database;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.waheed.bassem.alamir.utils.Utils;

public class StoreItem implements Parcelable {

    private String id;
    private String name;
    private String city;
    private String area;
    private String phone;
    private String imageBase64;

    public StoreItem() {
        this.id = String.valueOf(System.currentTimeMillis());
        this.name = null;
        this.city = null;
        this.area = null;
        this.phone = null;
        this.imageBase64 = null;
    }

    public StoreItem(String id, String name, String city, String area, String phone, String imageBase64) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.area = area;
        this.phone = phone;
        this.imageBase64 = imageBase64;
    }

    protected StoreItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        city = in.readString();
        area = in.readString();
        phone = in.readString();
        imageBase64 = in.readString();
    }

    public static final Creator<StoreItem> CREATOR = new Creator<StoreItem>() {
        @Override
        public StoreItem createFromParcel(Parcel in) {
            return new StoreItem(in);
        }

        @Override
        public StoreItem[] newArray(int size) {
            return new StoreItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public Bitmap getImageBitmap() {
        return Utils.base64ToBitmap(imageBase64);
    }

    public void setImageBase64(Bitmap bitmap) {
        imageBase64 = Utils.bitmapToBase64(bitmap);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(phone);
        dest.writeString(imageBase64);
    }

    public String getFullAddress () {
        return  city + " - " + area;
    }

    public String getNavigationAddress() {
        return  city + " - " + area;
    }
}
