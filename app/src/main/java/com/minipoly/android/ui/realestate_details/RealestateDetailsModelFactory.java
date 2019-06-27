package com.minipoly.android.ui.realestate_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minipoly.android.entity.Realestate;

public class RealestateDetailsModelFactory implements ViewModelProvider.Factory {
    private Realestate realestate;

    public RealestateDetailsModelFactory(Realestate realestate) {
        this.realestate = realestate;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RealestateDetailsViewModel(realestate);
    }
}
