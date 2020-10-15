package com.places.findapizza.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Place implements Parcelable {

    private int distance = 0;

    @Expose
    @SerializedName("place_id")
    private String id;

    @Expose
    @SerializedName("vicinity")
    private String vicinity;

    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("rating")
    private String rating;

    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    protected Place(Parcel in) {
        geometry = (Geometry) in.readParcelable(Geometry.class.getClassLoader());
        openingHours = (OpeningHours) in.readParcelable(OpeningHours.class.getClassLoader());
        id = in.readString();
        vicinity = in.readString();
        icon = in.readString();
        name = in.readString();
        rating = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(geometry, i);
        parcel.writeParcelable(openingHours, i);
        parcel.writeString(id);
        parcel.writeString(vicinity);
        parcel.writeString(icon);
        parcel.writeString(name);
        parcel.writeString(rating);
    }
}
