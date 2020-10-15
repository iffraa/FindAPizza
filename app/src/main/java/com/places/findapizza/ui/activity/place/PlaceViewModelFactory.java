package com.places.findapizza.ui.activity.place;


import android.content.Context;

import com.places.findapizza.data.network.services.ApiClient;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PlaceViewModelFactory implements ViewModelProvider.Factory {

    private final ApiClient apiClient;
    private Context  context;

    public PlaceViewModelFactory(ApiClient apiClient, Context context) {
        this.apiClient = apiClient;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlaceViewModel.class)) {
            return (T) new PlaceViewModel(apiClient, context);
        }

        throw new IllegalArgumentException("Unknown ViewModel clasthiss");
    }

}
