package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemAdBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.param_managers.RealestateManager;

import java.util.List;

public class AdAdapter extends ListAdapter<Realestate, AdAdapter.AdHolder> {

    private static final DiffUtil.ItemCallback<Realestate> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Realestate>() {

                @Override
                public boolean areItemsTheSame(@NonNull Realestate category, @NonNull Realestate t1) {
                    return category.getId().equals(t1.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Realestate category, @NonNull Realestate t1) {
                    return category.getText().equals(t1.getText());
                }
            };

    public AdAdapter() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public AdHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemAdBinding binding = ListItemAdBinding.inflate(inflater);
        return new AdHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdHolder adHolder, int i) {
        adHolder.bind(getItem(i));
    }


    public class AdHolder extends RecyclerView.ViewHolder {
        public List<String> tags;
        public MutableLiveData<Realestate> realestate = new MutableLiveData<>();
        private ListItemAdBinding binding;

        public AdHolder(@NonNull ListItemAdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Realestate r) {
            realestate.setValue(r);
            tags = RealestateManager.getTags(r.getRealestateInfo());
            binding.setM(this);
        }

    }
}
