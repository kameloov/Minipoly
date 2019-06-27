package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemCurrencyBinding;
import com.minipoly.android.entity.Currency;

public class CurrencyAdapter extends ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder> {
    public CurrencyAdapter() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Currency> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Currency>() {


                @Override
                public boolean areItemsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                    return oldItem.getName().equals(newItem.getName());
                }
            };


    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemCurrencyBinding binding = ListItemCurrencyBinding.inflate(inflater);
        return new CurrencyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder categoryViewHolder, int i) {
        categoryViewHolder.bind(getItem(i));
    }


    public class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private ListItemCurrencyBinding binding;

        public CurrencyViewHolder(@NonNull ListItemCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Currency currency) {
            binding.setC(currency);
        }

    }
}
