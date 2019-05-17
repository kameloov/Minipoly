package com.minipoly.android.repository;

import android.location.Address;
import android.location.Geocoder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minipoly.android.entity.Realestate;

import java.lang.reflect.Type;
import java.util.List;

public class RealestateRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference ads = db.collection("realestate");
    private static String url = "";

    public static void addRealestate(Realestate realestate) {
        String id = ads.document().getId();
        realestate.setId(id);
        ads.document(id).set(realestate);
    }

    public static void getRealestates(MutableLiveData<List<Realestate>> liveData) {
         new StringRequest(Request.Method.GET, url,
                response ->{
                    if (response!=null && !response.isEmpty()){
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Realestate>>(){}.getType();
                        liveData.setValue(gson.fromJson(response,type));
                    }
                },
                error -> {

                });
    }

    public static Realestate generateRealestate(double lat, double lng, Geocoder geocoder) {
        Realestate realestate = new Realestate();
        realestate.setLang(lng);
        realestate.setLat(lat);
        if (Geocoder.isPresent()) {
            try {
                Address address = geocoder.getFromLocation(lat, lng, 1).get(0);
                realestate.setCountryId(address.getCountryCode());
                Address city = geocoder.getFromLocationName(address.getLocality(), 1).get(0);
                // city id is the location elements separated by _
                realestate.setCityId(city.getLatitude() + "_" + city.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return realestate;
    }

}
