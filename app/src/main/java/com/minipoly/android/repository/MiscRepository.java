package com.minipoly.android.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.minipoly.android.C;
import com.minipoly.android.DataListener;
import com.minipoly.android.entity.Car;
import com.minipoly.android.entity.City;
import com.minipoly.android.entity.ComputerMisc;
import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.MobileMisc;
import com.minipoly.android.entity.ValueFilter;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.utils.FireStoreUtils;

import java.util.List;

import static com.minipoly.android.References.cars;
import static com.minipoly.android.References.computer;
import static com.minipoly.android.References.countries;
import static com.minipoly.android.References.db;
import static com.minipoly.android.References.mobile;

public class MiscRepository {

    public static void addCountry(Country country) {

    }

    public static void addCountries(List<Country> countries) {
        for (Country c : countries)
            addCountry(c);
    }


    public static void addCity(City city) {
        DocumentReference reference = countries.document(city.getCountryId())
                .collection("city").document(city.getId());
        if (!FireStoreUtils.documentExists(reference)) {
            reference.set(city);
        }
    }

    public static void getcity(String id, String countryId, DataListener<City> listener) {
        DocumentReference reference = countries.document(countryId).collection("city")
                .document(id);
        reference.get().addOnCompleteListener(task -> {
            City city = null;
            if (task.isSuccessful() && task.getResult() != null)
                city = task.getResult().toObject(City.class);
            listener.onComplete(task.isSuccessful(), city);
        });
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

    public static void setMobileMisc(MobileMisc misc) {
        mobile.document(C.MISC).set(misc);
    }

    public static void setComputerMisc(ComputerMisc misc) {
        computer.document(C.MISC).set(misc);
    }

    public static FireLiveDocument<ComputerMisc> getComputerMisc() {
        return new FireLiveDocument<>(computer.document(C.MISC).get(), ComputerMisc.class);
    }

    public static FireLiveDocument<MobileMisc> getMobileMisc() {
        return new FireLiveDocument<>(mobile.document(C.MISC).get(), MobileMisc.class);
    }

    public static FireLiveQuery<Country> getCountries() {
        return new FireLiveQuery<Country>(countries.orderBy("name").get(), Country.class);
    }

    public static void getCountries(DataListener<List<Country>> listener) {
        countries.get().addOnCompleteListener(task -> {
            List<Country> countries = null;
            if (task.isSuccessful() && task.getResult() != null)
                countries = task.getResult().toObjects(Country.class);
            listener.onComplete(task.isSuccessful(), countries);
        });
    }

    public static void getCountries(ValueFilter<Double> lang, MutableLiveData liveData) {
        Query query = FireStoreUtils.processFilter(countries, lang);
        if (query == null)
            query = countries;
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null)
                liveData.setValue(task.getResult().toObjects(Country.class));
        });
    }

    public static void getCities(DataListener<List<City>> listener) {
        db.collectionGroup("city").get().addOnCompleteListener(task -> {
            List<City> cities = null;
            if (task.isSuccessful() && task.getResult() != null)
                cities = task.getResult().toObjects(City.class);
            listener.onComplete(task.isSuccessful(), cities);
        });
    }

    public static FireLiveQuery<Country> getCities(String countryId) {
        return new FireLiveQuery<Country>(countries.document(countryId).collection(C.COLLECTION_CITY).orderBy("name").get(), Country.class);
    }

}
