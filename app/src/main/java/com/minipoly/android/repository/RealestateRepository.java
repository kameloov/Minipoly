package com.minipoly.android.repository;

import android.location.Address;
import android.location.Geocoder;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minipoly.android.C;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Realestate;

import java.util.List;

public class RealestateRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference ads = db.collection(C.COLLECTION_REALESTATE);


    public static void addRealestate(Realestate realestate) {
        String id = ads.document().getId();
        realestate.setId(id);
        ads.document(id).set(realestate);
    }

    public static void addRealestate(Realestate realestate, CompleteListener listener) {
        String id = ads.document().getId();
        realestate.setId(id);
        ads.document(id).set(realestate).addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public static void getRealestates(DataListener<List<Realestate>> listener) {
        ads.get().addOnCompleteListener(task -> listener.onComplete(task.isSuccessful(),
                task.isSuccessful() ? task.getResult().toObjects(Realestate.class) : null));
    }

    public static void like(String id) {
        ads.document(id).update("like", FieldValue.increment(1));
    }

    public static void dislike(String id) {
        ads.document(id).update("dislike", FieldValue.increment(1));
    }


    public static Realestate generateRealestate(double lat, double lng, Geocoder geocoder) {
        Realestate realestate = new Realestate();
        realestate.setLang(lng);
        realestate.setLat(lat);
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
