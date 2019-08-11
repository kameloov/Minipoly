package com.minipoly.android.repository;

import android.location.Address;
import android.location.Geocoder;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Auction;
import com.minipoly.android.entity.Bid;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;

import java.util.List;

import static com.minipoly.android.References.auctions;
import static com.minipoly.android.References.db;

public class AuctionRepository {

    public static void addAuction(Auction auction, CompleteListener listener) {
        DocumentReference reference = auctions.document();
        auction.setId(reference.getId());
        reference.set(auction).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static FireLiveDocument<Auction> getAuction(String auctionId) {
        return new FireLiveDocument<Auction>(auctions.document(auctionId).get(), Auction.class);
    }

    public static void getAuction(String auctionId, DataListener<Auction> listener) {
        auctions.document(auctionId).get().addOnCompleteListener(task -> {
            Auction auction = null;
            if (task.isSuccessful() && task.getResult() != null)
                auction = task.getResult().toObject(Auction.class);
            listener.onComplete(task.isSuccessful(), auction);
        });
    }

    public static FireLiveQuery<Auction> getAuctions(boolean active) {
        return new FireLiveQuery<>(auctions.whereEqualTo("active", active).get(), Auction.class);
    }

    public static void getAuctions(DataListener<List<Auction>> listener) {
        auctions.whereEqualTo("active", true).get().addOnCompleteListener(task -> {
            List<Auction> auctions = null;
            if (task.isSuccessful())
                auctions = task.getResult().toObjects(Auction.class);
            listener.onComplete(task.isSuccessful(), auctions);
        });

    }

    public static void like(String id, DataListener<Boolean> listener) {
        DocumentReference reference = auctions.document(id);
        SocialRepository.like(reference, listener);
    }

    public static void dislike(String id, DataListener<Boolean> listener) {
        DocumentReference reference = auctions.document(id);
        SocialRepository.dislike(reference, listener);
    }

    public static void block(String auctionId, String userId) {
        auctions.document(auctionId).update("blocked", FieldValue.arrayUnion(userId));
    }

    public static FireLiveQuery<Auction> getUserAuctions(String userId) {
        return new FireLiveQuery<>(auctions.whereEqualTo("advrt.userBrief.id", userId).get(), Auction.class);
    }

    public static void updateViews(String id) {
        auctions.document(id).update("views", FieldValue.increment(1));
    }

    public static void addBid(Bid bid, CompleteListener listener) {
        String auctionId = bid.getAdvrtId();
        db.runTransaction(transaction -> {
            Auction auction = transaction.get(auctions.document(auctionId)).toObject(Auction.class);
            if (auction.getLastBid() == null || auction.getLastBid().getValue() < bid.getValue()) {
                DocumentReference reference = auctions.document(auctionId).collection(C.COLLECTION_BIDS).document();
                bid.setId(reference.getId());
                transaction.set(reference, bid);
                auction.setLastBid(bid);
                transaction.set(auctions.document(auctionId), auction);
                return true;
            } else {
                transaction.set(auctions.document(auctionId), auction);
                return false;
            }

        }).addOnCompleteListener(task -> listener.onComplete(task.getResult()));
    }

    public static FireLiveQuery<Bid> getBids(String auctionId) {
        return new FireLiveQuery<>(auctions.document(auctionId).collection(C.COLLECTION_BIDS)
                .orderBy("value", Query.Direction.DESCENDING).get(), Bid.class);
    }


    public static FireLiveQuery<Bid> getTopBids(String auctionId, int count) {
        return new FireLiveQuery<>(auctions.document(auctionId).collection(C.COLLECTION_BIDS)
                .orderBy("value", Query.Direction.DESCENDING).limit(count).get(), Bid.class);
    }

    public static FireLiveDocument<Auction> watchAuction(String auctionId) {
        return new FireLiveDocument<Auction>(auctions.document(auctionId), Auction.class);
    }

    public static Auction generateAuction(double lat, double lng, Geocoder geocoder) {
        Auction realestate = new Auction(true);
        realestate.setLang(lng);
        realestate.setLat(lat);
        realestate.setCityName("Cairo");
        realestate.setCityNameAR("القاهرة");
        if (Geocoder.isPresent()) {
            try {
                Address address = geocoder.getFromLocation(lat, lng, 1).get(0);
                realestate.setCountryId(address.getCountryCode());
                Address city = geocoder.getFromLocationName(address.getAddressLine(1), 1).get(0);
                // city id is the location elements separated by _
                realestate.setCityId(city.getLatitude() + "_" + city.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return realestate;
    }
}
