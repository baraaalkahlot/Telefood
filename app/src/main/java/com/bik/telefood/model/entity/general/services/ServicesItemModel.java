package com.bik.telefood.model.entity.general.services;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesItemModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("favorite")
    private Boolean favorite;

    protected ServicesItemModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        img = in.readString();
        price = in.readString();
        byte tmpFavorite = in.readByte();
        favorite = tmpFavorite == 0 ? null : tmpFavorite == 1;
    }

    public static final Creator<ServicesItemModel> CREATOR = new Creator<ServicesItemModel>() {
        @Override
        public ServicesItemModel createFromParcel(Parcel in) {
            return new ServicesItemModel(in);
        }

        @Override
        public ServicesItemModel[] newArray(int size) {
            return new ServicesItemModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(img);
        dest.writeString(price);
        dest.writeByte((byte) (favorite == null ? 0 : favorite ? 1 : 2));
    }
}
