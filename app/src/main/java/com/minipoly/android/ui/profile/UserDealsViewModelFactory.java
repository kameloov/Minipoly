package com.minipoly.android.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minipoly.android.entity.Realestate;

import java.util.List;

public class UserDealsViewModelFactory implements ViewModelProvider.Factory {
    private String userId;
    private List<Realestate> ads;

    public UserDealsViewModelFactory(String userId, List<Realestate> ads) {
        this.userId = userId;
        this.ads = ads;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserDealsViewModel(userId, ads);
    }
}
