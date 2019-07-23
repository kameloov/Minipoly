package com.minipoly.android.ui.bidders;

import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Bid;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.AuctionRepository;

public class BiddersViewModel extends ViewModel {
    private String auctionId;
    public FireLiveQuery<Bid> bids;

    public BiddersViewModel(String auctionId) {
        this.auctionId = auctionId;
        if (auctionId != null)
            bids = AuctionRepository.getBids(auctionId);
    }
}
