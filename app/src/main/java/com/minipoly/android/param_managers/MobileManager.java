package com.minipoly.android.param_managers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.entity.MobileInfo;

import java.util.ArrayList;
import java.util.List;

public class MobileManager {
    private MutableLiveData<MobileInfo> mobileInfo = new MutableLiveData<>();
    public MutableLiveData<Integer> storageIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> colorIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> ramIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> batteryIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> typeIndex = new MutableLiveData<>(0);
    public List<String> ram = new ArrayList<>();
    public List<String> storage = new ArrayList<>();
    public List<String> colors = new ArrayList<>();
    public List<String> battery = new ArrayList<>();
    public List<String> type = new ArrayList<>();

    public MobileManager() {
        mobileInfo.setValue(new MobileInfo());
        prepareStorage();
        prepareColors();
        prepareRam();
        prepareBattery();
        prepareType();
    }

    public LiveData<MobileInfo> getMobileInfo() {
        return mobileInfo;
    }


    private void prepareStorage() {
        ArrayList<String> list = new ArrayList<>();
        list.add(" Storage");
        list.add("2 G");
        list.add("4 G");
        list.add("8 G");
        list.add("16 G");
        list.add("32 G");
        list.add("64 G");
        list.add("128 G");
        list.add("256 G");
        list.add("512 G");
        storage = list;
    }

    private void prepareColors() {
        ArrayList<String> c = new ArrayList<>();
        c.add("Color");
        c.add("White");
        c.add("Black");
        c.add("Blue");
        c.add("Red");
        c.add("Gray");
        c.add("Silver");
        colors = c;
    }

    private void prepareBattery() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Battery");
        items.add("1400 mAH");
        items.add("1800 mAH");
        items.add("2000 mAH");
        items.add("2200 mAH");
        items.add("2400 mAH");
        items.add("2800 mAH");
        items.add("3000 mAH");
        items.add("4000 mAH");
        battery = items;
    }

    private void prepareType() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Type");
        items.add("Tablet");
        items.add("Mobile");
        items.add("transformer");
        type = items;
    }

    private void prepareRam() {
        ArrayList<String> c = new ArrayList<>();
        c.add("Ram");
        c.add("1 G");
        c.add("1.5 G");
        c.add("2 G");
        c.add("3 G");
        c.add("4 G");
        c.add("6 G");
        c.add("8 G");
        c.add("16 G");
        ram = c;
    }
}
