package com.minipoly.android.ui.more_dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minipoly.android.entity.Realestate;

public class MoreModelFactory implements ViewModelProvider.Factory {
    private Realestate realestate;

    public MoreModelFactory(Realestate realestate) {
        this.realestate = realestate;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MoreViewModel(realestate);
    }
}
