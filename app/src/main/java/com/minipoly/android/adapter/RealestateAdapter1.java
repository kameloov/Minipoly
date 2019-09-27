package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemAdvrt1Binding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.param_managers.RealestateManager;

public class RealestateAdapter1 extends ListAdapter<Realestate, RealestateAdapter1.RelestateViewHolder> {

    private static final DiffUtil.ItemCallback<Realestate> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Realestate>() {

                @Override
                public boolean areItemsTheSame(@NonNull Realestate oldItem, @NonNull Realestate newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Realestate oldItem, @NonNull Realestate newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }
            };
    private IRealestateListener listener;

    public RealestateAdapter1() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public RelestateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemAdvrt1Binding binding = ListItemAdvrt1Binding.inflate(inflater);
        return new RelestateViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull RelestateViewHolder relestateViewHolder, int i) {
        relestateViewHolder.bind(getItem(i));
    }

    public void setListener(IRealestateListener listener) {
        this.listener = listener;
    }


    public interface IRealestateListener {
        void onRealestateSelected(Realestate realestate);
    }

    public class RelestateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemAdvrt1Binding binding;

        public RelestateViewHolder(@NonNull ListItemAdvrt1Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
            int width = binding.imageView94.getLayoutParams().width;
            int height = (int) (width * 1.5f);
            binding.imageView94.getLayoutParams().height = height;

        }

        public void bind(Realestate realestate) {
            binding.setAd(realestate);
            binding.setArabic(false);
            binding.getRoot().setOnClickListener(this);
            binding.setLst(RealestateManager.getTags(realestate.getRealestateInfo()));

        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onRealestateSelected(binding.getAd());
        }
    }
}
