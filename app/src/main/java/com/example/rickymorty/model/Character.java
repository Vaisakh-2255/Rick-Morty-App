package com.example.rickymorty.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Character implements Parcelable {
    private String id;
    private String name;
    private String status;
    private String imageUrl;
    private String origin;
    private String location;
    private boolean isFavorite; // Add this line

    public Character(String id, String name, String status, String imageUrl, String origin, String location) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.imageUrl = imageUrl;
        this.origin = origin;
        this.location = location;
        this.isFavorite = false; // Default value
    }

    protected Character(Parcel in) {
        id = in.readString();
        name = in.readString();
        status = in.readString();
        imageUrl = in.readString();
        origin = in.readString();
        location = in.readString();
        isFavorite = in.readByte() != 0; // Read the favorite status
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLocation() {
        return location;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(status);
        parcel.writeString(imageUrl);
        parcel.writeString(origin);
        parcel.writeString(location);
        parcel.writeByte((byte) (isFavorite ? 1 : 0)); // Write the favorite status
    }
}
