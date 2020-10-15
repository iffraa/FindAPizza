package com.places.findapizza.ui.activity.details;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailViewModelFactory implements ViewModelProvider.Factory {

    private Context  context;

    public DetailViewModelFactory( Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(context);
        }

        throw new IllegalArgumentException("Unknown ViewModel clasthiss");
    }

}
