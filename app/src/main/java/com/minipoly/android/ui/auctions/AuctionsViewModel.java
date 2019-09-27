package com.minipoly.android.ui.auctions;

import androidx.lifecycle.ViewModel;

import com.minipoly.android.ActivityViewModel;
import com.minipoly.android.entity.Auction;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.AuctionRepository;

public class AuctionsViewModel extends ViewModel implements ActivityViewModel.IKindListener {
    public FireLiveQuery<Auction> auctions;
    public Boolean kind = false;

    public AuctionsViewModel() {
        auctions = AuctionRepository.getAuctions(true);
        ActivityViewModel.addKindListener(this);
    }


    private void refresh() {

    }

    @Override
    public void onKindChanged(boolean kind) {
        this.kind = kind;
        refresh();
    }
}
