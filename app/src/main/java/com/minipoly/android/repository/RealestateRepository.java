package com.minipoly.android.repository;

import android.location.Address;
import android.location.Geocoder;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.utils.LocalData;

import java.util.List;

import static com.minipoly.android.References.realestates;

public class RealestateRepository {


    public static void addRealestate(Realestate realestate) {
        String id = realestates.document().getId();
        realestate.setId(id);
        realestates.document(id).set(realestate);
    }

    public static void updateViews(String id) {
        realestates.document(id).update("views", FieldValue.increment(1));
    }

    public static void addRealestate(Realestate realestate, CompleteListener listener) {
        String id = realestates.document().getId();
        realestate.setId(id);
        realestates.document(id).set(realestate).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }


    public static void follow(String id, CompleteListener listener) {
        UserBrief brief = new UserBrief(LocalData.getUserInfo());
        realestates.document(id).collection(C.COLLECTION_FOLLOWERS).document(brief.getId())
                .set(brief).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));

    }

    public static void unfollow(String id, CompleteListener listener) {
        UserBrief brief = new UserBrief(LocalData.getUserInfo());
        realestates.document(id).collection(C.COLLECTION_FOLLOWERS).document(brief.getId())
                .delete().addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));

    }

    public static void isFollowing(String id, DataListener<Boolean> listener) {
        UserBrief brief = new UserBrief(LocalData.getUserInfo());
        realestates.document(id).collection(C.COLLECTION_FOLLOWERS).document(brief.getId()).get()
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(), task.getResult().exists()));
    }

    public static void getRealestates(DataListener<List<Realestate>> listener) {
        realestates.get().addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(),
                task.isSuccessful() ? task.getResult().toObjects(Realestate.class) : null));
    }

    public static void getUserRealestates(String userId, DataListener<List<Realestate>> listener) {
        realestates.whereEqualTo("userBrief.id", userId).get().addOnCompleteListener(task -> {
            List<Realestate> realestates = null;
            if (task.isSuccessful() && task.getResult() != null)
                realestates = task.getResult().toObjects(Realestate.class);
            listener.onComplete(task.isSuccessful(), realestates);
        });
    }

    public static void getRealestate(String id, DataListener<Realestate> listener) {
        realestates.document(id).get().addOnCompleteListener(task -> {
            Realestate realestate = null;
            if (task.getResult() != null)
                realestate = task.getResult().toObject(Realestate.class);
            listener.onComplete(task.isSuccessful(), realestate);
        });
    }

    public static void like(String id, DataListener<Boolean> listener) {
        DocumentReference reference = realestates.document(id);
        SocialRepository.like(reference, listener);
    }

    public static void dislike(String id, DataListener<Boolean> listener) {
        DocumentReference reference = realestates.document(id);
        SocialRepository.dislike(reference, listener);
    }

    public static Realestate generateRealestate(double lat, double lng, Geocoder geocoder) {
        Realestate realestate = new Realestate();
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
