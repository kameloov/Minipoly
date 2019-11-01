package com.minipoly.android;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.minipoly.android.entity.Realestate;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import timber.log.Timber;

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

    public static void getRealestates(LatLng min, LatLng max, DataListener<List<Realestate>> listener) {
        StringRequest request = new StringRequest(Request.Method.GET,
                buildUrl(min, max, 0, 100000, false, "Mobile"),
                response -> {
                    List<Realestate> data = null;
                    Log.e(TAG, "getAds: " + response);
                    if (response != null && !response.isEmpty()) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Realestate>>() {
                        }.getType();
                        listener.onComplete(true, gson.fromJson(response, type));
                    } else
                        listener.onComplete(false, null);
                },
                error -> Timber.e("getAds: error"));
        queue.add(request);
    }

    private static String round(double value) {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value);
    }

    private static String buildUrl(LatLng min, LatLng max, float minPrice, float maxPrice, boolean rent, String category) {
        String url = realestateUrl + "?minLat=" + round(min.getLatitude()) + "&minLng=" + round(min.getLongitude()) + "&maxLng=" +
                round(max.getLongitude()) + "&maxLat=" + round(max.getLatitude()) + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice +
                "&rent=" + rent + "&category=" + category;
        Log.e(TAG, "buildUrl: " + url);
        return url;
    }
}
