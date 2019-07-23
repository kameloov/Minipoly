package com.minipoly.android.ui.auction_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.R;
import com.minipoly.android.UserManager;
import com.minipoly.android.adapter.CommentAdapter;
import com.minipoly.android.databinding.AuctionDetailsFragmentBinding;
import com.minipoly.android.entity.Auction;

public class AuctionDetails extends Fragment {

    private AuctionDetailsViewModel model;
    private AuctionDetailsFragmentBinding binding;
    private CommentAdapter commentAdapter = new CommentAdapter();
    private float oldPrice = 0;

    public static AuctionDetails newInstance() {
        return new AuctionDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AuctionDetailsFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Auction auction = AuctionDetailsArgs.fromBundle(getArguments()).getAuction();
        model = ViewModelProviders.of(this, new AuctionDetailsModelFactory(auction))
                .get(AuctionDetailsViewModel.class);
        prepareCommentsAdapter();
        binding.setLifecycleOwner(this);
        binding.setM(model);
        binding.setUserId(UserManager.getUserID());
        attachObservers();
    }

    private void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    private void attachObservers() {
        model.command.observe(this, command -> {
            if (command == AuctionDetailsViewModel.Command.BLOCKED)
                toast(getString(R.string.blocked));
            if (command == AuctionDetailsViewModel.Command.LAST_BIDDER)
                toast(getString(R.string.last_bidder));

            model.command.setValue(AuctionDetailsViewModel.Command.IDLE);
        });

        model.getComments().observe(this, comments -> commentAdapter.submitList(comments));
        model.watching.observe(this, aBoolean -> {
            Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
            binding.imgBell.startAnimation(a);
        });

        model.getRealestate().observe(this, auction -> {
            float price = auction.getLastBid() == null ? auction.getPrice() : auction.getLastBid().getValue();
            model.bidManager.setBasePrice(price);
            if (oldPrice != price) {
                Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.reward);
                a.setRepeatCount(1);
                binding.textView24.startAnimation(a);
                oldPrice = price;
            }

        });
    }


    private void prepareCommentsAdapter() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        binding.lstComments.setLayoutManager(manager);
        binding.lstComments.setAdapter(commentAdapter);
    }
}
