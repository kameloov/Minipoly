package com.minipoly.android.ui.edit_advrt;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minipoly.android.entity.Realestate;

public class EditAdvrtModelFactory implements ViewModelProvider.Factory {
    private Realestate realestate;

    public EditAdvrtModelFactory(Realestate realestate) {
        this.realestate = realestate;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditAdvrtViewModel(realestate);
    }
}
