package com.minipoly.android.ui.filters.realestate;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minipoly.android.filters.RealestateFilter;

public class RealestateFilterFactory implements ViewModelProvider.Factory {
    private RealestateFilter filter;

    public RealestateFilterFactory(RealestateFilter filter) {
        this.filter = filter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RealestateFilterViewModel(filter);
    }
}
