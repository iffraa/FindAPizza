package com.places.findapizza.ui.activity.place;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.places.findapizza.R;
import com.places.findapizza.data.network.model.Location;
import com.places.findapizza.data.network.model.Place;
import com.places.findapizza.data.network.services.ApiClient;
import com.places.findapizza.ui.activity.BaseActivity;
import com.places.findapizza.ui.activity.details.DetailsActivity;
import com.places.findapizza.utilities.AdsUtility;
import com.places.findapizza.utilities.GPSTracker;
import com.places.findapizza.utilities.MapUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class PlaceActivity extends BaseActivity implements OnMapReadyCallback, PlaceAdapter.OnPlaceAdapter {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_record)
    TextView noRecord;

    private SupportMapFragment mapView;
    private GoogleMap map;
    private PlaceViewModel mapViewModel;
    private ApiClient apiClient;
    PlaceAdapter adapter;
    private MapUtility mapUtility;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        mapView = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        apiClient = new ApiClient();

        setBannerAd();

        mapViewModel = createViewModel();
        initializeMap();

        mapViewModel.loadPlaces();
        MutableLiveData<ArrayList<Place>> places = mapViewModel.getPlaces();
        if (places != null) {
            places.observe(this, new PlaceObserver());
            noRecord.setVisibility(View.GONE);
        }
        else
            noRecord.setVisibility(View.VISIBLE);

    }

    private void setBannerAd()
    {
        AdView adView = (AdView)findViewById(R.id.adView);
        AdsUtility adsUtility = new AdsUtility();
        adsUtility.initialzeAds(this,adView);
    }

    private void initializeMap() {
        mapUtility = new MapUtility();
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private PlaceViewModel createViewModel() {
        PlaceViewModelFactory factory = new PlaceViewModelFactory(apiClient, this);
        return ViewModelProviders.of(this, factory).get(PlaceViewModel.class);
    }

    @Override
    public void onPlaceClicked(Place place) {
        Intent intent = new Intent(PlaceActivity.this, DetailsActivity.class);
        intent.putExtra("place", place);
        startActivity(intent);
    }

    private void setAdapter(List<Place> places) {
        adapter = new PlaceAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItems(places,this);
    }

    private class PlaceObserver implements Observer<List<Place>> {

        @Override
        public void onChanged(@Nullable List<Place> places) {
            if (places == null)
                return;
            else {

                for(int i = 0; i < places.size(); i++ ){
                    setDistance(places.get(i));
                }

                sortList(places);
                setAdapter(places);
                setMapMarkers(places);

                mapUtility.addUserMarker(map,PlaceActivity.this);
                mapUtility.setMapBounds(mapView,map);
            }
            if (places.isEmpty()) {
                noRecord.setVisibility(View.VISIBLE);
            } else {
                noRecord.setVisibility(View.GONE);
            }
        }
    }

    public void setDistance(Place place) {
        int distance = 0;
        Location placeLocation = place.getGeometry().getLocation();
        if(placeLocation != null)
        {
            GPSTracker gpsTracker = new GPSTracker(this);
            distance = gpsTracker.getDistance(placeLocation.getLat(),placeLocation.getLng());
            place.setDistance(distance);
        }

    }

    private void sortList(List<Place> places)
    {
        Collections.sort(places, new Comparator<Place>() {
            public int compare(Place p1, Place p2) {
                return p1.getDistance() - p2.getDistance();
            }
        });
    }

    private void setMapMarkers(List<Place> places)
    {
        for(int i = 0; i < places.size(); i++)
        {
            Place place = places.get(i);
            if(place != null)
                mapUtility.addMarker(map,place);
        }
    }

}
