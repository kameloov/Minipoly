package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemSpinnerBinding;
import com.minipoly.android.entity.Category;
import com.minipoly.android.ui.category_dialog.CategoryDialog;

public class CategorySpinnerAdapter extends ListAdapter<Category, CategorySpinnerAdapter.CategoryViewHolder> {
    private CategoryDialog.CategorySelectListener listener;

    public CategorySpinnerAdapter() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Category> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Category>() {

                @Override
                public boolean areItemsTheSame(@NonNull Category category, @NonNull Category t1) {
                    return category.getId().equals(t1.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Category category, @NonNull Category t1) {
                    return category.getTitle().equals(t1.getTitle());
                }
            };

    public void setListener(CategoryDialog.CategorySelectListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemSpinnerBinding binding = ListItemSpinnerBinding.inflate(inflater);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.bind(getItem(i));
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemSpinnerBinding binding;

        public CategoryViewHolder(@NonNull ListItemSpinnerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.getRoot().setOnClickListener(this);
            binding.setC(category);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onCategoorySelected(false, binding.getC());

            }
        }
    }
}
