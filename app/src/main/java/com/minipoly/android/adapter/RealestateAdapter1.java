package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.R;
import com.minipoly.android.databinding.ListItemAdvrt1Binding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.Toe;
import com.minipoly.android.num.ToeType;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            binding.setNow(new Date());
            binding.getRoot().setOnClickListener(this);
            List<Toe> list = new ArrayList<>();
            ToeType type = isOffer(realestate) ? ToeType.OFFER : ToeType.NORMAL;
            if (realestate.isMarket()) {
                if (realestate.getCategoryId().equalsIgnoreCase("car"))
                    list = CarManager.getToes(realestate.getCarInfo(), type);
                if (realestate.getCategoryId().equalsIgnoreCase("mobile"))
                    list = MobileManager.getToes(realestate.getMobileInfo(), type);
                if (realestate.getCategoryId().equalsIgnoreCase("computer"))
                    list = ComputerManager.getToes(realestate.getComputerInfo(), type);

            } else
                list = RealestateManager.getToes(realestate.getRealestateInfo(), type);
            binding.setLst(list);
            String red;
            if (realestate.isMarket())
                red = binding.getRoot().getContext()
                        .getString(realestate.isUsed() ? R.string.used_tag : R.string.new_tag);
            else
                red = binding.getRoot().getContext()
                        .getString(realestate.getRealestateInfo().isRent() ? R.string.rent : R.string.sell);
            binding.setRed(red);
        }

        private boolean isOffer(Realestate r) {
            Date now = new Date();
            return r.getOfferEnd() != null && r.getOfferEnd().after(now);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onRealestateSelected(binding.getAd());
        }
    }
}
