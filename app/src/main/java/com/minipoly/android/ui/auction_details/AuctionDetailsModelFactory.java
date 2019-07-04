package com.minipoly.android.ui.auction_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minipoly.android.entity.Auction;

public class AuctionDetailsModelFactory implements ViewModelProvider.Factory {
    private Auction auction;

    public AuctionDetailsModelFactory(Auction auc) {
        this.auction = auc;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuctionDetailsViewModel(auction);
    }
}
