package com.minipoly.android.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minipoly.android.R;
import com.minipoly.android.entity.Category;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryViewHolder> {
    public CategoryAdapter() {
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


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_category, viewGroup, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.bind(getItem(i));
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }

        public void bind(Category category) {
            txtTitle.setText(category.getTitle());
        }
    }
}
