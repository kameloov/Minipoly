package com.minipoly.android.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AboutViewModelFactory implements ViewModelProvider.Factory {

    private String userId;

    public AboutViewModelFactory(String userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserAboutViewModel(userId);
    }
}
