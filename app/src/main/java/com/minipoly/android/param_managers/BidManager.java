package com.minipoly.android.param_managers;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.minipoly.android.BR;
import com.minipoly.android.entity.Bid;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.utils.LocalData;

public class BidManager extends BaseObservable {
    private float basePrice;
    private int[] prices = new int[5];
    private float[] percents = new float[]{1.015f, 1.003f, 1.005f, 1.08f, 1.1f};
    private String auctionId;
    private int index = 2;

    public BidManager(String auctionId) {
        this.auctionId = auctionId;
    }

    @Bindable
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        notifyPropertyChanged(BR.index);
    }

    public float getBasePrice() {
        return basePrice;
    }

    private void updatePrices() {
        for (int i = 0; i < percents.length; i++)
            prices[i] = (int) (basePrice * percents[i]);
        notifyPropertyChanged(BR.prices);
    }

    public void bid() {
        Bid bid = new Bid();
        bid.setBidder(new UserBrief(LocalData.getUserInfo()));
        bid.setAdvrtId(auctionId);
        bid.setValue(prices[index]);
        AuctionRepository.addBid(bid, success -> {

        });
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
        updatePrices();
    }

    @Bindable
    public int[] getPrices() {
        return prices;
    }
}
