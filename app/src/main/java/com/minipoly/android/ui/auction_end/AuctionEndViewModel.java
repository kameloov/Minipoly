package com.minipoly.android.ui.auction_end;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.minipoly.android.R;
import com.minipoly.android.entity.Auction;
import com.minipoly.android.entity.Bid;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.utils.SocialUtils;

public class AuctionEndViewModel extends ViewModel {
    public FireLiveQuery<Bid> bids;
    public FireLiveDocument<Auction> auction;

    public AuctionEndViewModel(String auctionId) {
        auction = AuctionRepository.getAuction(auctionId);
        bids = AuctionRepository.getTopBids(auctionId, 4);
    }

    public void contact(View v) {
        Auction a = auction.getValue();
        String phone = a.getUserBrief().getPhone();
        String txt = v.getContext().getString(R.string.auction_end_contact);
        txt = a.getUserBrief().getName() + " " + txt + " " + a.getTitle();
        SocialUtils.whatsappMsg(v.getContext(), phone, txt);
    }
}
