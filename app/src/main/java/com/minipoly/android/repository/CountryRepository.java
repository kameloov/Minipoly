package com.minipoly.android.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.ValueFilter;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.utils.FireStoreUtils;

import java.util.List;

public class CountryRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference countries = db.collection("country");
    private static MutableLiveData<List<Country>> countryList;
    private static final String TAG = "CountryRepository";

    public static void addCountry(Country country){
        countries.document(country.getId()).set(country);
    }

    public static void addCountries(List<Country> countries){
        for (Country c  : countries)
            addCountry(c);
    }

    public static FireLiveQuery<Country> getCountries(ValueFilter<Double> lang){
        Log.e(TAG, "getCountries: getting countries");
        Query query = FireStoreUtils.processFilter(countries,lang);
        return new FireLiveQuery<>(query,Country.class);
    }

}
