package com.places.findapizza.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.places.findapizza.R;

import androidx.core.app.ActivityCompat;

public class GPSTracker implements LocationListener {
	private final Context mContext;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	Location location = null;
	double latitude;
	double longitude;

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);

			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				mContext.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				
			} else {
			    if(!hasPermissions())
                {
                    requestPermisssion();
                }

					this.canGetLocation = true;
					if (isNetworkEnabled) {
						locationManager.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("Network", "Network Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
							    saveLocation();
							}
						}
					}
					if (isGPSEnabled) {
						if (location == null) {
							locationManager.requestLocationUpdates(
									LocationManager.GPS_PROVIDER,
									MIN_TIME_BW_UPDATES,
									MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
							Log.d("GPS", "GPS Enabled");
							if (locationManager != null) {
								location = locationManager
										.getLastKnownLocation(LocationManager.GPS_PROVIDER);
								if (location != null) {
								    saveLocation();
								}
							}
						}
					}
				}

		} catch (SecurityException e) {
            UIUtility.showLongToast(e.toString(),mContext);
			e.printStackTrace();
		}

		return location;
	}

	private void saveLocation()
    {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            PreferenceUtility.saveObjectToSharedPreference(mContext, mContext.getString(R.string.location_key),location);
    }

    //get distance in km
    public int getDistance(double place_lat,double place_long)
    {
        int distance = 0;
       // Location user_location = PreferenceUtility.getSavedObjectFromPreference(mContext,mContext.getString(R.string.location_key),Location.class);
		Location user_location = Constants.location;
        Location placeLocation = new Location("");//provider name is unnecessary
        placeLocation.setLatitude(place_lat);//your coords of course
        placeLocation.setLongitude(place_long);

        float distanceInMeters =  placeLocation.distanceTo(user_location);
		double distInKm = distanceInMeters*0.001;
		distance = Math.round((float) distInKm);

        return distance;
    }

	private void requestPermisssion()
    {
        ActivityCompat.requestPermissions((Activity)mContext, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION },1);
    }

	private boolean hasPermissions()
	{
		if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			UIUtility.showShortToast("First enable LOCATION ACCESS in settings.",mContext);
			return false;
		}

		return true;
	}

	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		return latitude;
	}

	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}
		return longitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		latitude = arg0.getLatitude();
		longitude = arg0.getLongitude();

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}