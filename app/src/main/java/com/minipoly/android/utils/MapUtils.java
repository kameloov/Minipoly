package com.minipoly.android.utils;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.mapboxsdk.Mapbox;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.City;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapUtils {

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
}
