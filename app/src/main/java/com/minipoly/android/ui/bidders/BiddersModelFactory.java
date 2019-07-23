package com.minipoly.android.ui.bidders;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BiddersModelFactory implements ViewModelProvider.Factory {

    private String auctionId;

    public BiddersModelFactory(String auctionId) {
        this.auctionId = auctionId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BiddersViewModel(auctionId);
    }
}
