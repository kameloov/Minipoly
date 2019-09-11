package com.minipoly.android.ui.location_dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LocationViewModelFactory implements ViewModelProvider.Factory {
    String countryId;

    public LocationViewModelFactory(String countryId) {
        this.countryId = countryId;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LocationDialogViewModel(countryId);
    }
}
