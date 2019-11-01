package com.minipoly.android.utils;

import android.graphics.Point;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.City;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapUtils {

    public static double L0_STEP = 0.0625;
    public static double L1_STEP = 0.125;
    public static double L2_STEP = 0.25;
    public static double L3_STEP = 0.5;
    public static double L4_STEP = 1;
    public static double L5_STEP = 2;
    public static double L6_STEP = 4;
    public static double L7_STEP = 8;
    public static double L8_STEP = 16;
    public static double L9_STEP = 32;
    public static double L10_STEP = 64;
    public static double L11_STEP = 128;

    public static double[] steps = {L0_STEP, L1_STEP, L2_STEP, L3_STEP, L4_STEP, L5_STEP,
            L6_STEP, L7_STEP, L8_STEP, L9_STEP, L10_STEP, L11_STEP};

    public static void cityById(String id, String country, DataListener<City> listener) {
        MapboxGeocoding geocoding = MapboxGeocoding.builder().accessToken(Mapbox.getAccessToken())
                .query(id).build();
        geocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                if (response.body() == null)
                    listener.onComplete(false, null);
                CarmenFeature feature = response.body().features().get(0);

                if (feature != null) {
                    City city = new City();
                    city.setId(id);
                    city.setName(feature.placeName());
                    city.setNameAr(feature.placeName());
                    city.setLat(feature.center().latitude());
                    city.setLang(feature.center().longitude());
                    city.setCountryId(country);
                    listener.onComplete(true, city);
                } else
                    listener.onComplete(false, null);
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onComplete(false, null);
            }
        });
    }

    public static int getZoomLevel(LatLngBounds bounds) {
        int index = -1;
        double max = bounds.getLatitudeSpan() > bounds.getLongitudeSpan() ? bounds.getLatitudeSpan() : bounds.getLongitudeSpan();
        for (int i = 0; i < steps.length; i++) {
            if (steps[i] > max) {
                index = i;
                break;
            }
        }
        if (index == -1)
            return 11;
        String ne = getCoordinatesString(index, bounds.getNorthEast());
        String nw = getCoordinatesString(index, bounds.getNorthWest());
        String sw = getCoordinatesString(index, bounds.getSouthWest());
        String se = getCoordinatesString(index, bounds.getSouthEast());
        index = (ne.equals(nw) && ne.equals(se) && ne.equals(sw)) ? index : index + 1;
        return index > 11 ? 11 : index;
    }

    public static Point getCoordinates(int level, LatLng latLng) {
        Point p = new Point();
        p.x = (int) (latLng.getLatitude() / steps[level]) + 1;
        p.y = (int) (latLng.getLongitude() / steps[level]) + 1;
        return p;
    }


    public static String getCoordinatesString(int level, LatLng latLng) {
        Point p = new Point();
        p.x = (int) (latLng.getLatitude() / steps[level]) + 1;
        p.y = (int) (latLng.getLongitude() / steps[level]) + 1;
        return p.x + "_" + p.y;
    }
}
