package com.minipoly.android.ui.category_dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {
    String cateoryId;

    public CategoryViewModelFactory(String cateoryId) {
        this.cateoryId = cateoryId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CategoryDialogViewModel(cateoryId);
    }
}
