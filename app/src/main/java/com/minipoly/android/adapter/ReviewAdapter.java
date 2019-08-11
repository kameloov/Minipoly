package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemReviewBinding;
import com.minipoly.android.entity.Review;

public class ReviewAdapter extends ListAdapter<Review, ReviewAdapter.ReviewViewHolder> {

    private static final DiffUtil.ItemCallback<Review> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Review>() {

                @Override
                public boolean areItemsTheSame(@NonNull Review category, @NonNull Review t1) {
                    return category.getId().equals(t1.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Review category, @NonNull Review t1) {
                    return category.getText().equals(t1.getText());
                }
            };

    public ReviewAdapter() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemReviewBinding binding = ListItemReviewBinding.inflate(inflater);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        reviewViewHolder.bind(getItem(i));
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        private ListItemReviewBinding binding;

        public ReviewViewHolder(@NonNull ListItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Review review) {

        }

    }
}
