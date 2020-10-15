package com.places.findapizza.ui.activity.details;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;
import com.places.findapizza.R;
import com.places.findapizza.data.network.model.Detail;
import com.places.findapizza.data.network.model.DetailResponse;
import com.places.findapizza.data.network.model.Geometry;
import com.places.findapizza.data.network.model.Location;
import com.places.findapizza.data.network.model.Place;
import com.places.findapizza.data.network.services.ApiClient;
import com.places.findapizza.data.network.services.ApiInterface;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsViewModel extends ViewModel {

    MutableLiveData<Detail> placeData;
    Context context;
    Place place;

    public DetailsViewModel(Context context) {
        this.context = context;
        this.placeData = new MutableLiveData<>();
    }

    public void loadPlaceData(Intent intent) {
        assert intent.getExtras() != null;
        place = intent.getExtras().getParcelable("place");
        if(place != null)
        {
            String placeId  = place.getId();
            if(placeId != null)
                getPlaceDetails(placeId);

        }
    }


    public LatLng getPlaceLocation()
    {
        LatLng latLng = null;
        if(place != null)
        {
            Geometry geometry = place.getGeometry();
            Location location = geometry.getLocation();
            latLng = new LatLng(location.getLat(),location.getLng());
        }

        return latLng;
    }

    public Place getPlace()
    {
        return place;
    }


    public MutableLiveData<Detail> getPlaceDetails() {
        return placeData;
    }

    public void getPlaceDetails(String placeId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String key = context.getResources().getString(R.string.api_key);
        String fields = "name,website,formatted_address,icon,opening_hours,formatted_phone_number";

        Call<DetailResponse> call = apiService.getPlaceDetails(placeId,fields,key + "");
        call.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {

                DetailResponse place = (DetailResponse) response.body();
                if(place != null)
                {
                    Detail data = place.getDetail();
                    placeData.postValue(data);
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


  /*  public void getPlaceDetails(String placeId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String key = context.getResources().getString(R.string.api_key);
        String fields = "name,website,formatted_address,icon,opening_hours,formatted_phone_number";

        Call<JsonObject> call = apiService.getPlaceDetails(placeId,fields,key + "");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject place = (JsonObject) response.body();
                if(place != null)
                {
                 //   placeData.postValue(place);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

*/
}
