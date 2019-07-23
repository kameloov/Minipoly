package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemTopBidderBinding;
import com.minipoly.android.entity.Bid;
import com.minipoly.android.utils.SocialUtils;

public class TopBiddersAdapter extends ListAdapter<Bid, TopBiddersAdapter.BidHolder> {

    private static final ItemCallback<Bid> DIFF_CALLBACK = new ItemCallback<Bid>() {

        @Override
        public boolean areItemsTheSame(@NonNull Bid oldItem, @NonNull Bid newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Bid oldItem, @NonNull Bid newItem) {
            return oldItem.getValue() == newItem.getValue() && oldItem.getId().equals(newItem.getId());
        }
    };

    public TopBiddersAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemTopBidderBinding binding = ListItemTopBidderBinding.inflate(inflater);
        return new BidHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BidHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class BidHolder extends RecyclerView.ViewHolder {
        public ListItemTopBidderBinding binding;

        public BidHolder(@NonNull ListItemTopBidderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Bid bid) {
            binding.setBid(bid);
            binding.setIndex(getAdapterPosition());
            binding.btnMessage.setOnClickListener(v ->
                    SocialUtils.whatsappMsg(v.getContext(), bid.getBidder().getPhone(), ""));
        }
    }
}
