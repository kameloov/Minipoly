package com.minipoly.android.param_managers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.minipoly.android.entity.MobileInfo;
import com.minipoly.android.entity.MobileMisc;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.repository.MiscRepository;

import java.util.ArrayList;
import java.util.List;

public class MobileManager {
    private MobileInfo mobileInfo = new MobileInfo();
    private MutableLiveData<Integer> storageIndex = new MutableLiveData<>(-1);
    private MutableLiveData<Integer> colorIndex = new MutableLiveData<>(-1);
    private MutableLiveData<Integer> ramIndex = new MutableLiveData<>(-1);
    private MutableLiveData<Integer> batteryIndex = new MutableLiveData<>(-1);
    private MutableLiveData<Integer> typeIndex = new MutableLiveData<>(-1);
    public FireLiveDocument<MobileMisc> misc = MiscRepository.getMobileMisc();
    public LiveData<List<String>> battery = Transformations.switchMap(misc, input -> {
        MutableLiveData<List<String>> result = new MutableLiveData<>();
        if (misc.getValue() != null) {
            List<String> dest = new ArrayList<>();
            List<Integer> src = misc.getValue().getBattery();
            for (int i : src)
                dest.add(i + " mAh");
            result.setValue(dest);
        }
        return result;

    });

    public Integer getStorageIndex() {
        return storageIndex.getValue();
    }

    public void setStorageIndex(Integer storageIndex) {
        this.storageIndex.setValue(storageIndex);
        if (storageIndex > -1)
            mobileInfo.setStorage(misc.getValue().getStorage().get(storageIndex));
    }


    public Integer getColorIndex() {
        return colorIndex.getValue();
    }

    public void setColorIndex(Integer colorIndex) {
        this.colorIndex.setValue(colorIndex);
        if (colorIndex > -1)
            mobileInfo.setColor(misc.getValue().getColors().get(colorIndex));
    }


    public Integer getRamIndex() {
        return ramIndex.getValue();
    }

    public void setRamIndex(Integer ramIndex) {
        this.ramIndex.setValue(ramIndex);
        if (ramIndex > -1)
            mobileInfo.setRam(misc.getValue().getRam().get(ramIndex));

    }

    public Integer getBatteryIndex() {
        return batteryIndex.getValue();
    }

    public void setBatteryIndex(Integer batteryIndex) {
        this.batteryIndex.setValue(batteryIndex);
        if (batteryIndex > -1)
            mobileInfo.setBattery(misc.getValue().getBattery().get(batteryIndex));
    }

    public Integer getTypeIndex() {
        return typeIndex.getValue();
    }

    public void setTypeIndex(Integer typeIndex) {
        this.typeIndex.setValue(typeIndex);
        if (typeIndex > -1)
            mobileInfo.setCategory(misc.getValue().getType().get(typeIndex));
    }

    public MobileInfo getMobileInfo() {
        return mobileInfo;
    }
}
