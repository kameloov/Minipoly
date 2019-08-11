package com.minipoly.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.R;
import com.minipoly.android.databinding.ListItemBidBinding;
import com.minipoly.android.entity.Bid;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.SocialUtils;

public class BidAdapter extends ListAdapter<Bid, BidAdapter.BidHolder> {

    private View options;
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

    public BidAdapter(View options) {
        super(DIFF_CALLBACK);
        this.options = options;
    }

    @NonNull
    @Override
    public BidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBidBinding binding = ListItemBidBinding.inflate(inflater);
        return new BidHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BidHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private void showMsg(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    class BidHolder extends RecyclerView.ViewHolder {
        public ListItemBidBinding binding;

        public BidHolder(@NonNull ListItemBidBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Bid bid) {
            binding.setBid(bid);
            binding.imageView46.setOnClickListener(v -> {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.user_pop);
                menu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.call:
                            if (bid.getBidder().getPhone() != null)
                                SocialUtils.dial(v.getContext(), bid.getBidder().getPhone());
                            break;
                        case R.id.msg:
                            if (bid.getBidder().getPhone() != null) {
                                boolean done = SocialUtils.whatsappMsg(v.getContext(), bid.getBidder().getPhone(), "Hello");
                                if (!done)
                                    showMsg(v.getContext(), "Whatsapp is not installed");
                            }
                            break;
                        case R.id.follow:
                            SocialRepository.follow(bid.getBidder(), success -> {
                                showMsg(v.getContext(), "followed");
                            });
                            break;
                        case R.id.view_profile:

                            break;

                        case R.id.block:
                            AuctionRepository.block(bid.getAdvrtId(), bid.getBidder().getId());
                            showMsg(v.getContext(), "Blocked");
                            break;
                    }
                    return true;
                });
                menu.show();
            });
        }
    }
}
