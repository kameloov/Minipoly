package com.minipoly.android.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Car;
import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.ValueFilter;
import com.minipoly.android.utils.FireStoreUtils;

import java.util.List;

import static com.minipoly.android.References.cars;

public class MiscRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference countries = db.collection("country");
    private static MutableLiveData<List<Country>> countryList;
    private static final String TAG = "MiscRepository";

    public static void addCountry(Country country) {
        countries.document(country.getId()).set(country);
    }

    public static void addCountries(List<Country> countries) {
        for (Country c : countries)
            addCountry(c);
    }

    public static void addCar(Car car) {
        DocumentReference reference = cars.document();
        car.setId(reference.getId());
        reference.set(car);
    }

    public static void addCarModel(String carId, String model) {
        DocumentReference reference = cars.document(carId);
        reference.update("brands", FieldValue.arrayUnion(model));
    }


    public static void getCars(DataListener<List<Car>> listener) {
        cars.get().addOnCompleteListener(task -> {
            boolean b = (task.isSuccessful() && task.getResult() != null);
            listener.onComplete(task.isSuccessful(), b ? task.getResult().toObjects(Car.class) : null);

        });
    }


 /*   public static FireLiveQuery<Country> getCountries(ValueFilter<Double> lang) {
        Log.e(TAG, "getCountries: getting countries");
        Query query = FireStoreUtils.processFilter(countries, lang);
        return new FireLiveQuery(query, Country.class);
    }*/

    public static void getCountries(ValueFilter<Double> lang, MutableLiveData liveData) {
        Query query = FireStoreUtils.processFilter(countries, lang);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null)
                liveData.setValue(task.getResult().toObjects(Country.class));
        });
    }

}
