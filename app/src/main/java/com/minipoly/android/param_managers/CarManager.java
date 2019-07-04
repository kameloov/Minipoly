package com.minipoly.android.param_managers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

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
    public MutableLiveData<Integer> modelIndex = new MutableLiveData<>();
    public MutableLiveData<CustomRadio> transmission = new MutableLiveData<>();
    public MutableLiveData<List<String>> brands = new MutableLiveData<>();
    //public MutableLiveData<List<String>> models = new MutableLiveData<>();
    public List<Car> brandsObj = new ArrayList<>();
    public List<String> years = new ArrayList<>();
    public List<String> colors = new ArrayList<>();
    public List<String> fuel = new ArrayList<>();
    public LiveData<List<String>> models = Transformations.switchMap(brandIndex, input -> {
        MutableLiveData<List<String>> result = new MutableLiveData<>();
        ArrayList<String> lst = new ArrayList<>();
        lst.add("Car model");
        int index = input - 1;
        if (index > -1 && brandsObj != null && brandsObj.size() > 0)
            lst.addAll(brandsObj.get(index).getModels());
        result.setValue(lst);
        return result;
    });

    public CarManager() {
        carInfo.setValue(new CarInfo());
        prepareYears();
        prepareColors();
        prepareBrands();
        preprareFuel();
        transmission.setValue(new CustomRadio(false, "auto", "manual"));
    }

    public LiveData<CarInfo> getCarInfo() {
        return carInfo;
    }

    public CarInfo buildInfo() {
        CarInfo info = new CarInfo();
        info.setAutomatic(transmission.getValue().isChecked());
        int bi = brandIndex.getValue();
        info.setBrand(bi > 0 ? brands.getValue().get(bi) : null);
        int ci = colorIndex.getValue();
        info.setColor(ci > 0 ? colors.get(bi) : null);
        int fi = fuelIndex.getValue();
        info.setFuel(fi > 0 ? fuel.get(fi) : null);
        String v = kilometer.getValue();
        if (v != null && !v.isEmpty())
            info.setKilometres(Integer.parseInt(v));
        int mi = modelIndex.getValue();
        info.setType(mi > 0 ? models.getValue().get(mi) : null);
        return info;
    }


    private void prepareYears() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> list = new ArrayList<>();
        list.add("Select Year");
        for (int i = 1920; i <= year + 1; i++)
            list.add(String.valueOf(i));
        years = list;
    }

    private void prepareColors() {
        ArrayList<String> c = new ArrayList<>();
        c.add("Select color");
        c.add("White");
        c.add("Black");
        c.add("Blue");
        c.add("Red");
        c.add("Gray");
        c.add("Silver");
        colors = c;
    }

    private void preprareFuel() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Fuel type");
        items.add("Electric");
        items.add("Gasoline");
        items.add("Diesel");
        items.add("Hybrid");
        fuel = items;
    }

    private void prepareBrands() {
        ArrayList<String> c = new ArrayList<>();
        c.add("Car Brand");
        MiscRepository.getCars((success, data) -> {
            if (success && data != null) {
                brandsObj = data;
                for (FilterObject object : data)
                    c.add(object.getName());
            }
            brands.setValue(c);
        });
    }
}
