package com.example.inventoryapp.ui.dashboard;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InventoryDataItem implements Parcelable {
    String id, title, category, quantity;

    protected InventoryDataItem(Parcel in) {
        id = in.readString();
        title = in.readString();
        category = in.readString();
        quantity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InventoryDataItem> CREATOR = new Creator<InventoryDataItem>() {
        @Override
        public InventoryDataItem createFromParcel(Parcel in) {
            return new InventoryDataItem(in);
        }

        @Override
        public InventoryDataItem[] newArray(int size) {
            return new InventoryDataItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public InventoryDataItem(String id, String title, String category, String quantity) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.quantity = quantity;
    }
}
