package com.places.findapizza.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.places.findapizza.R;
import com.places.findapizza.utilities.UIUtility;


public class TestActivuty extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        UIUtility.showLongToast("toastttttt",this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


    }
}
