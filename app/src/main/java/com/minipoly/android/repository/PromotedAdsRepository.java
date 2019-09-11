package com.minipoly.android.repository;

import com.google.firebase.firestore.DocumentReference;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.PromotedAd;

import java.util.List;

import static com.minipoly.android.References.promotedAds;

public class PromotedAdsRepository {

    public static void addPromotedAd(PromotedAd ad, CompleteListener listener) {
        DocumentReference reference = promotedAds.document();
        ad.setId(reference.getId());
        reference.set(ad).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static void getAds(String countryId, DataListener<List<PromotedAd>> listener) {
        promotedAds.whereEqualTo("countryId", countryId).get().addOnCompleteListener(task -> {
            List<PromotedAd> ads = null;
            if (task.isSuccessful() && task.getResult() != null)
                ads = task.getResult().toObjects(PromotedAd.class);
            listener.onComplete(task.isSuccessful(), ads);
        });
    }
}
