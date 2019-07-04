package com.minipoly.android.repository;

import com.google.firebase.firestore.DocumentReference;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.entity.Auction;
import com.minipoly.android.entity.Bid;
import com.minipoly.android.livedata.FireLiveQuery;

import static com.minipoly.android.References.auctions;

public class AuctionRepository {

    public static void addAuction(Auction auction, CompleteListener listener) {
        DocumentReference reference = auctions.document();
        auction.setId(reference.getId());
        reference.set(auction).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static FireLiveQuery<Auction> getAuctions(boolean active) {
        return new FireLiveQuery<>(auctions.whereEqualTo("active", active).get(), Auction.class);
    }

    public static FireLiveQuery<Auction> getAuctions(String userId) {
        return new FireLiveQuery<>(auctions.whereEqualTo("advrt.userBrief.id", userId).get(), Auction.class);
    }

    public static void addBid(String auctionId, Bid bid, CompleteListener listener) {
        DocumentReference reference = auctions.document(auctionId).collection(C.COLLECTION_BIDS).document(auctionId);
        bid.setId(reference.getId());
        reference.set(bid);
    }

    public static FireLiveQuery<Bid> getBids(String auctionId) {
        return new FireLiveQuery<>(auctions.document(auctionId).collection(C.COLLECTION_BIDS).get(), Bid.class);
    }
}
