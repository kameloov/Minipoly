package com.minipoly.android.ui.category_dialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Category;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.CategoryRepository;

public class CategoryDialogViewModel extends ViewModel {
    private String categoryId;
    private FireLiveQuery<Category> categories;
    public MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public CategoryDialogViewModel(String categoryId, boolean market) {
        this.categoryId = categoryId;
        loading.setValue(true);
        if (categoryId == null)
            categories = CategoryRepository.getCategories(market);
        else
            categories = CategoryRepository.getSubcategories(categoryId);
    }

    public FireLiveQuery<Category> getCategories() {
        return categories;
    }

}
