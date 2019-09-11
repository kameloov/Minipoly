package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemCountryBinding;
import com.minipoly.android.entity.Country;
import com.minipoly.android.ui.location_dialog.LocationDialog;

public class CountryAdapter extends ListAdapter<Country, CountryAdapter.CountryViewHolder> {
    private static final DiffUtil.ItemCallback<Country> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Country>() {
                @Override
                public boolean areItemsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                    return newItem.getId().equals(oldItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
                    return newItem.getName().equals(oldItem.getName());
                }
            };
    private LocationDialog.LocationSelectListener listener;

    public CountryAdapter() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    public void setListener(LocationDialog.LocationSelectListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemCountryBinding binding = ListItemCountryBinding.inflate(inflater);
        return new CountryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder countryViewHolder, int i) {
        countryViewHolder.bind(getItem(i));
    }


    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemCountryBinding binding;

        public CountryViewHolder(@NonNull ListItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Country country) {
            binding.getRoot().setOnClickListener(this);
            binding.setC(country);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onCountrySelected(false, binding.getC());

            }
        }
    }
}
