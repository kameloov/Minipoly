package com.minipoly.android.repository;

import android.location.Address;
import android.location.Geocoder;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minipoly.android.entity.Realestate;

public class RealestateRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference ads = db.collection("realestate");


    public static void addRealestate(Realestate realestate) {
        String id = ads.document().getId();
        realestate.setId(id);
        ads.document(id).set(realestate);
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
