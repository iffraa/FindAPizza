package com.places.findapizza.data.network.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class OpeningHours implements Parcelable {
    @SerializedName("open_now")
    @Expose
    private boolean openNow;

    @Expose
    @SerializedName("weekday_text")
    private ArrayList<String> weekdayText = new ArrayList<>();

    public ArrayList<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(ArrayList<String> weekdayText) {
        this.weekdayText = weekdayText;
    }

    protected OpeningHours(Parcel in) {
        openNow = in.readBoolean();
    }

    public static final Creator<OpeningHours> CREATOR = new Creator<OpeningHours>() {
        @Override
        public OpeningHours createFromParcel(Parcel in) {
            return new OpeningHours(in);
        }

        @Override
        public OpeningHours[] newArray(int size) {
            return new OpeningHours[size];
        }
    };

    public boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(openNow);
    }
}
