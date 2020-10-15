package com.places.findapizza.data.network.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class NearbyResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<Place> resultList = new ArrayList<>();

    public  ArrayList<Place> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<Place> resultList) {
        this.resultList = resultList;
    }

}
