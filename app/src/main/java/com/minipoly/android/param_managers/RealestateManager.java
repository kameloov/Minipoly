package com.minipoly.android.param_managers;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.R;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.RealestateInfo;
import com.minipoly.android.entity.Toe;
import com.minipoly.android.num.ToeType;

import java.util.ArrayList;
import java.util.List;

public class RealestateManager {
    public RealestateInfo info = new RealestateInfo();
    public String area = "0";
    public String[] olds = new String[]{"0 - 2", "3 - 5", "6 - 10", "11 -20 ", "21 -50 ", " more"};
    public MutableLiveData<Integer> oldIndex = new MutableLiveData<>(0);
    public MutableLiveData<CustomRadio> typeRadio = new MutableLiveData<>(new CustomRadio(false, "For Rent", "For Sale"));

    public void changeRooms(int i) {
        info.setRoomCount(info.getRoomCount() + i);

    }

    public void changeFurnished(boolean furnished) {
        info.setFurnished(furnished);
    }

    public void changeMonthlyRent(boolean b) {
        info.setMonthlyRent(b);
    }

    public void changeYearlyRent(boolean b) {

        info.setYearlyRent(b);
    }

    public void changebath(int i) {

        info.setBathroomCount(info.getBathroomCount() + i);
    }

    public RealestateInfo getInfo() {
        info.setArea(Float.parseFloat(area));
        info.setRent(typeRadio.getValue().isChecked());
        return info;
    }


    public static List<Toe> getToes(RealestateInfo info, ToeType toeType) {
        ArrayList<Toe> lst = new ArrayList<>();
        if (info != null) {
            lst.add(new Toe(info.getRoomCount() + "", R.drawable.ic_bedroom, toeType));
            lst.add(new Toe(info.getBathroomCount() + "", R.drawable.ic_shower, toeType));
            lst.add(new Toe(info.isFurnished() ? "Furnished" : "empty", 0, toeType));
        } else {
            lst.add(null);
            lst.add(null);
            lst.add(null);
        }

        return lst;
    }

    public static List<String> getTags(RealestateInfo r) {
        ArrayList<String> list = new ArrayList<>();
        if (r != null) {
            list.add(r.isFurnished() ? "Furnished" : "Empty");
            if (r.getRoomCount() > 0)
                list.add(r.getRoomCount() + " Rooms");
            if (r.getBathroomCount() > 0)
                list.add(r.getBathroomCount() + " Bathrooms");
            if (r.getArea() > 0)
                list.add(r.getArea() + " M");
            if (r.isYearlyRent())
                list.add("Annual rent");
            if (r.isMonthlyRent())
                list.add("Monthly rent");
            if (r.getOld() > 0)
                list.add(r.getOld() + " Year");
        }
        if (list.size() < 7) {
            for (int i = 0; i <= 7 - list.size(); i++)
                list.add(null);
        }
        return list;
    }

}
