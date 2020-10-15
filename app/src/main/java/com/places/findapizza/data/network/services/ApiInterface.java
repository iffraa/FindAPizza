package com.places.findapizza.data.network.services;


import com.places.findapizza.data.network.model.DetailResponse;
import com.places.findapizza.data.network.model.NearbyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("place/details/json")
    abstract Call<DetailResponse> getPlaceDetails(@Query("place_id") String id,
                                         @Query("fields") String fields,
                                         @Query("key") String key);


    @GET("place/nearbysearch/json")
    abstract Call<NearbyResponse> getNearbyPlaces(@Query("location") String location,
                                                  @Query("radius") String radius,
                                                  @Query("type") String type,
                                                  @Query("keyword") String keyword,
                                                  @Query("key") String key);

}

