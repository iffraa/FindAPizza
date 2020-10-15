package com.places.findapizza.ui.activity.place;

import android.content.Context;
import android.location.Location;

import com.places.findapizza.R;
import com.places.findapizza.data.network.model.NearbyResponse;
import com.places.findapizza.data.network.model.Place;
import com.places.findapizza.data.network.services.ApiClient;
import com.places.findapizza.data.network.services.ApiInterface;
import com.places.findapizza.utilities.Constants;
import com.places.findapizza.utilities.GPSTracker;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlaceViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Place>> livePlacesData;
    private MutableLiveData<Boolean> isLoading;

    private ApiClient apiClient;
    private Context context;

    private String type;
    private String key;


    public PlaceViewModel(ApiClient apiClient, Context context) {
        this.apiClient = apiClient;
        this.context = context;
        livePlacesData =  new MutableLiveData<ArrayList<Place>>();
    }

    void loadPlaces() {
        GPSTracker gpsTracker = new GPSTracker(context);
        Location location = gpsTracker.getLocation();
        Constants.location = location;
        if(location != null)
            getNearbyPlaces(location.getLatitude(), location.getLongitude(), Constants.RADIUS);
    }

    MutableLiveData<ArrayList<Place>> getPlaces() {

        return livePlacesData;
    }

    MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }

    public void getNearbyPlaces(double latitude, double longitude, int radius) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String type = context.getResources().getString(R.string.type);
        String keyword = context.getResources().getString(R.string.keyword);
        String key = context.getResources().getString(R.string.api_key);

        String latLang = latitude + "," + longitude;

        Call<NearbyResponse> call = apiService.getNearbyPlaces(latLang, radius + "", type + "",keyword + "", key + "");
        call.enqueue(new Callback<NearbyResponse>() {
            @Override
            public void onResponse(Call<NearbyResponse> call, Response<NearbyResponse> response) {

                NearbyResponse resData = (NearbyResponse) response.body();
                ArrayList<Place> places = resData.getResultList();
                if(places != null && !places.isEmpty())
                {
                    setPlaces(places);
                }

            }

            @Override
            public void onFailure(Call<NearbyResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void setPlaces(ArrayList<Place> places) {
        livePlacesData.postValue(places);
    }



}
