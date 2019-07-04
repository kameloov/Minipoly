package com.minipoly.android.ui.category_dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {
    String cateoryId;
    boolean market;

    public CategoryViewModelFactory(String cateoryId, boolean market) {
        this.cateoryId = cateoryId;
        this.market = market;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CategoryDialogViewModel(cateoryId, market);
    }
}
