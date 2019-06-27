package com.minipoly.android.ui.category_dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.adapter.CategorySpinnerAdapter;
import com.minipoly.android.databinding.DialogCategoryBinding;
import com.minipoly.android.entity.Category;

public class CategoryDialog extends DialogFragment {

    private CategoryDialogViewModel model;
    private DialogCategoryBinding binding;
    private CategorySpinnerAdapter adapter = new CategorySpinnerAdapter();
    private static CategorySelectListener listener;
    private static String categoryId;

    public static CategoryDialog newInstance(String categoryId, CategorySelectListener listener) {
        CategoryDialog dialog = new CategoryDialog();
        CategoryDialog.listener = listener;
        CategoryDialog.categoryId = categoryId;
        return new CategoryDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        binding = DialogCategoryBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, new CategoryViewModelFactory(categoryId)).get(CategoryDialogViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareAdapter();
        attachObservers();
    }

    private void prepareAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        adapter.setListener((b, cat) -> {
            if (listener != null)
                listener.onCategoorySelected(categoryId != null, cat);
            else
                Log.e("Listener", "listener is null");
            dismiss();
        });
        binding.lstCategory.setAdapter(adapter);
        binding.lstCategory.setLayoutManager(manager);
    }

    private void attachObservers() {
        model.getCategories().observe(this, categories -> {
            adapter.submitList(categories);
            Log.e("count", "count: " + categories.size());
            model.loading.setValue(false);
        });
    }

    public interface CategorySelectListener {
        void onCategoorySelected(boolean sub, Category category);
    }
}
