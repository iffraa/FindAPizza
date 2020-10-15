package com.places.findapizza.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.places.findapizza.R;
import com.places.findapizza.ui.activity.place.PlaceActivity;
import com.places.findapizza.utilities.NetworkUtility;
import com.places.findapizza.utilities.UIUtility;

public class SplashActivity extends
        Activity {

    private static int TIME_OUT = 1500; //Time to launch the another activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(NetworkUtility.isInternetConnectionAvailable(this))
        {
            startActivityAfterDelay();
        }
        else
        {
            UIUtility.showAlert(getString(R.string.network_title),getString(R.string.network_message),this);
        }


    }

    private void startActivityAfterDelay()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, PlaceActivity.class);
                startActivity(i);
                finish();
               // UIUtility.showLongToast("toastttttt",SplashActivity.this);
            }
        }, TIME_OUT);
    }
}
