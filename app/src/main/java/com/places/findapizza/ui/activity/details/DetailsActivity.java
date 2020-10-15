package com.places.findapizza.ui.activity.details;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.places.findapizza.R;
import com.places.findapizza.data.network.model.Detail;
import com.places.findapizza.data.network.model.OpeningHours;
import com.places.findapizza.data.network.model.Route;
import com.places.findapizza.ui.activity.BaseActivity;
import com.places.findapizza.utilities.AdsUtility;
import com.places.findapizza.utilities.GPSTracker;
import com.places.findapizza.utilities.MapUtility;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.address_val)
    TextView addressValue;

    @BindView(R.id.time_val)
    TextView timeValue;

    @BindView(R.id.place_name)
    TextView placeName;

    @BindView(R.id.web_val)
    TextView webValue;

    @BindView(R.id.contact_val)
    TextView contactValue;

    @BindView(R.id.place_icon)
    ImageView placeIcon;

    private SupportMapFragment mapView;
    private GoogleMap map;
    private MapUtility mapUtility;
    private DetailsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setBannerAd();

        mapView = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        initializeMap();

        ButterKnife.bind(this);

        viewModel = createViewModel();

        viewModel.getPlaceDetails().observe(this, new PlaceObserver());

        viewModel.loadPlaceData(getIntent());

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

    private class PlaceObserver implements Observer<Detail> {
        @Override
        public void onChanged(@Nullable Detail detail) {
            if (detail == null) return;

            placeName.setText(detail.getName());
            addressValue.setText(detail.getFormattedAddress());
            contactValue.setText(detail.getFormattedPhoneNumber());
            webValue.setText(detail.getWebsite());

            setOpeningHrs(detail);

            Picasso.get().load(detail.getIcon()).into(placeIcon);

            mapUtility.addUserMarker(map, DetailsActivity.this);
            mapUtility.addMarker(map,viewModel.getPlace());

            //setRoute();

            mapUtility.setMapBounds(mapView,map);

        }
    }

    private void setRoute()
    {
        Route route = new Route();
        GPSTracker tracker = new GPSTracker(DetailsActivity.this);
        Location currentLocation = tracker.getLocation();

        LatLng currentLoc = new LatLng(currentLocation.getLatitude(),
                currentLocation.getLongitude());

        LatLng placeLoc = viewModel.getPlaceLocation();
        Document doc = route.getDocument(currentLoc,placeLoc ,
                route.MODE_DRIVING,this);
        if(doc != null)
            mapUtility.showMapRoute(map,doc);
    }

    private void setOpeningHrs(Detail detail) {
        OpeningHours openingHours = detail.getOpeningHours();
        if (openingHours != null) {
            ArrayList<String> hours = openingHours.getWeekdayText();
            for (int i = 0; i < hours.size(); i++) {
                String time = hours.get(i);
                timeValue.append(time + "\n");
            }
        }
    }

    private DetailsViewModel createViewModel() {
        DetailViewModelFactory factory = new DetailViewModelFactory(this);
        return ViewModelProviders.of(this, factory).get(DetailsViewModel.class);
    }



    public void onClick(View v) {
        TextView tv = (TextView) v;
        switch(tv.getId()) {
            case R.id.contact_val:
            case R.id.contact:
                callPlace();
                break;
            case R.id.web_val:
            case R.id.web_img:
                openWebPage();
                break;
        }
    }

    private void openWebPage()
    {
        String website = (String) webValue.getText();
        WebView theWebPage = new WebView(this);
        theWebPage.getSettings().setJavaScriptEnabled(true);
        theWebPage.getSettings().setPluginState(WebSettings.PluginState.ON);
        setContentView(theWebPage);
        theWebPage.loadUrl(website);
    }

    private void callPlace()
    {
        String contact_number = (String) contactValue.getText();
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel:" + contact_number));
        startActivity(call);
    }

}
