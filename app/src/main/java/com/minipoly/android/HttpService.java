package com.minipoly.android;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minipoly.android.entity.Realestate;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class HttpService {
    private static RequestQueue queue;
    private static String realestateUrl = "https://us-central1-minipoly-3010f.cloudfunctions.net/getRealestates";
    private static final String TAG = "HttpService";

    public static void init(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getQueue() {
        return queue;
    }

    public static void getRealestates(MutableLiveData<List<Realestate>> liveData, LatLng min,
                                      LatLng max, float minPrice, float maxPrice, boolean rent, String category) {
        StringRequest request = new StringRequest(Request.Method.GET,
                buildUrl(min, max, minPrice, maxPrice, rent, category),
                response -> {
                    Log.e(TAG, "getRealestates: " + response);
                    if (response != null && !response.isEmpty()) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Realestate>>() {
                        }.getType();
                        liveData.setValue(gson.fromJson(response, type));
                    }
                },
                error -> {
                    Log.e(TAG, "getRealestates: error");
                });
        queue.add(request);
    }

    private static String round(double value) {
        DecimalFormat df = new DecimalFormat("#.#######");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }

    private static String buildUrl(LatLng min, LatLng max, float minPrice, float maxPrice, boolean rent, String category) {
        String url = realestateUrl + "?minLat=" + round(min.latitude) + "&minLng=" + round(min.longitude) + "&maxLng=" +
                round(max.longitude) + "&maxLat=" + round(max.latitude) + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice +
                "&rent=" + rent + "&category=" + category;
        Log.e(TAG, "buildUrl: " + url);
        return url;
    }
}
