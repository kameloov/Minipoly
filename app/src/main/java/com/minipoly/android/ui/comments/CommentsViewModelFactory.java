package com.minipoly.android.ui.comments;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minipoly.android.entity.Realestate;

public class CommentsViewModelFactory implements ViewModelProvider.Factory {

    private Realestate realestate;

    public CommentsViewModelFactory(Realestate realestate) {
        this.realestate = realestate;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CommentsViewModel(realestate);
    }
}
