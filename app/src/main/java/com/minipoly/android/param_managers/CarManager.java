package com.minipoly.android.param_managers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.entity.Car;
import com.minipoly.android.entity.CarInfo;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.FilterObject;
import com.minipoly.android.repository.MiscRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CarManager {
    private MutableLiveData<CarInfo> carInfo = new MutableLiveData<>();
    public MutableLiveData<Integer> yearIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> colorIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> brandIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> fuelIndex = new MutableLiveData<>(0);
    public MutableLiveData<String> kilometer = new MutableLiveData<>();
    public MutableLiveData<Boolean> modelIndex = new MutableLiveData<>();
    public MutableLiveData<CustomRadio> transmission = new MutableLiveData<>();
    public List<String> brands = new ArrayList<>();
    public MutableLiveData<List<String>> models = new MutableLiveData<>();
    public List<Car> brandsObj = new ArrayList<>();
    public List<String> years = new ArrayList<>();
    public List<String> colors = new ArrayList<>();
    public List<String> fuel = new ArrayList<>();

    public CarManager() {
        carInfo.setValue(new CarInfo());
        prepareYears();
        prepareColors();
        prepareBrands();
        preprareFuel();
        transmission.setValue(new CustomRadio(false, "automatic", "manual"));
    }

    public LiveData<CarInfo> getCarInfo() {
        return carInfo;
    }


    private void prepareYears() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1920; i <= year; i++)
            list.add(String.valueOf(i));
        years = list;
    }

    private void prepareColors() {
        ArrayList<String> c = new ArrayList<>();
        c.add("White");
        c.add("Black");
        c.add("Blue");
        c.add("Red");
        c.add("Gray");
        c.add("Silver");
        colors = c;
    }

    public void loadModels() {
        int index = brandIndex.getValue();
        models.setValue(brandsObj.get(index).getModels());
    }

    private void preprareFuel() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Electric");
        items.add("Gasoline");
        items.add("Diesel");
        items.add("Hybrid");
        fuel = items;
    }

    private void prepareBrands() {
        ArrayList<String> c = new ArrayList<>();
        MiscRepository.getCars((success, data) -> {
            if (success && data != null) {
                brandsObj = data;
                for (FilterObject object : data)
                    c.add(object.getName());
            }
            models.setValue(brandsObj.get(brandIndex.getValue()).getModels());
            brands = c;
        });
    }
}
