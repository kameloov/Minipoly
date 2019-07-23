package com.minipoly.android.ui.auction_end;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AuctionEndModelFactory implements ViewModelProvider.Factory {
    private String auctionId;

    public AuctionEndModelFactory(String auctionId) {
        this.auctionId = auctionId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuctionEndViewModel(auctionId);
    }
}
