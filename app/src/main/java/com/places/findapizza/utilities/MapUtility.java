package com.places.findapizza.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.places.findapizza.data.network.model.Geometry;
import android.location.Location;
import com.places.findapizza.data.network.model.OpeningHours;
import com.places.findapizza.data.network.model.Place;
import com.places.findapizza.data.network.model.Route;
import com.places.findapizza.ui.activity.place.PlaceActivity;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class MapUtility {

    private LatLngBounds.Builder builder = new LatLngBounds.Builder();

    public Marker addMarker(GoogleMap map, Place place)
    {
        double  latitude = place.getGeometry().getLocation().getLat();
        double  longitude = place.getGeometry().getLocation().getLng();

        LatLng placeLatLong = new LatLng(latitude,
                longitude);

        Marker marker = map.addMarker(new MarkerOptions()
                .position(placeLatLong)
                .title(place.getName())
                .icon(getMarkerHue(place)));

        builder.include(placeLatLong);

        return marker;
    }

    public void setMapBounds(final SupportMapFragment mapFrag, final GoogleMap map) {
        try {
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),
                    450, 150, 0));
            map.moveCamera(CameraUpdateFactory.scrollBy(0, -65));
        } catch (IllegalStateException e) {
            if (mapFrag.getView().getViewTreeObserver().isAlive()) {
                mapFrag.getView()
                        .getViewTreeObserver()
                        .addOnGlobalLayoutListener(
                                new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @SuppressLint("NewApi")
                                    // We check which build version we are
                                    // using.
                                    @Override
                                    public void onGlobalLayout() {
                                        mapFrag.getView()
                                                .getViewTreeObserver()
                                                .removeGlobalOnLayoutListener(
                                                        this);
                                        map.moveCamera(CameraUpdateFactory
                                                .newLatLngBounds(
                                                        builder.build(), 450,
                                                        150, 0));
                                        map.moveCamera(CameraUpdateFactory
                                                .scrollBy(0, -65));
                                    }
                                });
            }
        }
    }

    public void showMapRoute(GoogleMap map, Document doc)
    {
        Route route = new Route();

        ArrayList<LatLng> directionPoint = route.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(4).color(
                Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }

        map.addPolyline(rectLine);
    }

    public void addUserMarker(GoogleMap map, Context context)
    {
        //get current location
        GPSTracker tracker = new GPSTracker(context);
        Location currentLocation = tracker.getLocation();

        LatLng currentLatLong = new LatLng(currentLocation.getLatitude(),
                currentLocation.getLongitude());

        map.addMarker(new MarkerOptions()
                .position(currentLatLong)
                .title("I'm Here :)")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        builder.include(currentLatLong);
    }

    public BitmapDescriptor getMarkerHue(Place place)
    {
        if(place.getOpeningHours() != null){
            boolean isOpen = place.getOpeningHours().getOpenNow();
            if(isOpen)
            {
                return BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            }
            else
            {
                return  BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }
       }

        return  BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

    }

}
