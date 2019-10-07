package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemAuctionBinding;
import com.minipoly.android.entity.Auction;
import com.minipoly.android.param_managers.MobileManager;

import java.util.List;

public class AuctionAdapter extends ListAdapter<Auction, AuctionAdapter.AuctionViewHolder> {

    private static final DiffUtil.ItemCallback<Auction> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Auction>() {

                @Override
                public boolean areItemsTheSame(@NonNull Auction oldItem, @NonNull Auction newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Auction oldItem, @NonNull Auction newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }
            };
    private IauctionListener listener;

    public AuctionAdapter() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    @NonNull
    @Override
    public AuctionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemAuctionBinding binding = ListItemAuctionBinding.inflate(inflater);
        return new AuctionViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull AuctionViewHolder relestateViewHolder, int i) {
        relestateViewHolder.bind(getItem(i));
    }

    public void setListener(IauctionListener listener) {
        this.listener = listener;
    }


    public interface IauctionListener {
        void onRealestateSelected(Auction auction);
    }

    public class AuctionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemAuctionBinding binding;

        public AuctionViewHolder(@NonNull ListItemAuctionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            int width = binding.imageView94.getLayoutParams().width;
            int height = (int) (width * 1.5f);
            binding.imageView94.getLayoutParams().height = height;
        }

        public void bind(Auction auction) {
            binding.setAd(auction);
            binding.setArabic(false);
            binding.getRoot().setOnClickListener(this);
            List<String> lst = null;
            if (auction.isMarket()) {
                lst = MobileManager.getMainTags(auction.getMobileInfo());
                // todo add other types
            } else {
                //  lst = RealestateManager.getToes(auction.getRealestateInfo());
            }
            binding.setLst(lst);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onRealestateSelected(binding.getAd());
        }
    }
}
