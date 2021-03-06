package com.minipoly.android.ui.newadvrt;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.minipoly.android.CompleteListener;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.City;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.utils.ImageBuffer;
import com.minipoly.android.utils.LocalData;
import com.minipoly.android.utils.MapUtils;
import com.minipoly.android.utils.SearchUtils;

import java.util.HashMap;
import java.util.List;

public class AddAdvrtDialogViewModel extends ViewModel {
    public enum Command {IDLE, SHOW_CATEGORY, SHOW_SUBCATEGORY}

    public CustomRadio kindRadio;
    public MutableLiveData<CustomRadio> usedRadio = new MutableLiveData<>();
    private MutableLiveData<Realestate> realestate = new MutableLiveData<>();
    public MutableLiveData<Category> category = new MutableLiveData<>();
    public MutableLiveData<Category> subCategory = new MutableLiveData<>();
    public String[] currencies = new String[]{"USD", "EUR", "SAR", "EGP", "SYP"};
    public MutableLiveData<Integer> index = new MutableLiveData<>(0);
    public MutableLiveData<String> price = new MutableLiveData<>();
    public MutableLiveData<Boolean> success = new MutableLiveData<>();
    public MutableLiveData<Boolean> extraVisible = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectedCategory = new MutableLiveData<>();
    public MobileManager mobileManager = new MobileManager();
    public MutableLiveData<Integer> old = new MutableLiveData<>(1);
    public MutableLiveData<RealestateManager> realestateManager = new MutableLiveData<>(new RealestateManager());
    public CarManager carManager = new CarManager();
    public ComputerManager computerManager = new ComputerManager();
    public MutableLiveData<Command> command = new MutableLiveData<>(Command.IDLE);
    public LiveData<List<Image>> images = ImageBuffer.getImages();

    public AddAdvrtDialogViewModel() {
        selectedCategory.setValue(0);
        kindRadio = new CustomRadio(false, "Add Realestate", "Add Market");
        usedRadio.setValue(new CustomRadio(false, "New", "Used"));
    }

    public void setCatOrSubId(boolean sub, Category cat) {
        if (sub)
            subCategory.setValue(cat);
        else
            category.setValue(cat);
    }

    public void changeOld(int value) {
        old.setValue(old.getValue() + value);
    }

    public void toggleExtra() {
        extraVisible.setValue(!extraVisible.getValue());
    }

    public LiveData<Realestate> getRealestate() {
        return realestate;
    }


    public void showSelect(boolean subcategory) {
        command.setValue(subcategory ? Command.SHOW_SUBCATEGORY : Command.SHOW_CATEGORY);
    }


    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public void setRealestate(Realestate realestate) {
        this.realestate.setValue( realestate);
    }

    public void setLocationData(City city, float lat, float lang) {
        Realestate r = realestate.getValue();
        r.setLat(lat);
        r.setLang(lang);
        r.setCityId(city.getId());
        r.setCountryId(city.getCountryId());
        r.setCityNameAR(city.getNameAr());
        r.setCityName(city.getName());
        LatLng latLng = new LatLng(lat, lang);
        for (int i = 0; i < 10; i++)
            r.setLevelCoord(i, MapUtils.getCoordinatesString(i, latLng));
        realestate.setValue(r);
    }

    public void addRealestate() {
        Realestate r = realestate.getValue();
        r.setPrice(Float.parseFloat(price.getValue()));
        r.setUserBrief(new UserBrief(LocalData.getUserInfo()));
        r.setImages(ImageBuffer.getImages().getValue());
        r.setUsed(!usedRadio.getValue().isChecked());
        r.setOld(old.getValue());
        r.setCurrency(currencies[index.getValue()]);
        boolean isMarket = !kindRadio.isChecked();
        r.setMarket(isMarket);
        r.setCategoryId(category.getValue() == null ? null : category.getValue().getId());
        if (category.getValue() != null) {
            Category c = category.getValue();
            r.setCategoryId(c.getId());
            r.setCategoryName(c.getTitle());
            r.setCategoryNameAr(c.getTitleAr());
        }

        if (subCategory.getValue() != null) {
            r.setSubCategoryId(subCategory.getValue().getId());
            r.setSubCatName(subCategory.getValue().getTitle());
            r.setSubCatNameAr(subCategory.getValue().getTitleAr());
        }
        if (!isMarket)
            r.setRealestateInfo(realestateManager.getValue().getInfo());
        else {
            String catId = category.getValue() == null ? "" : category.getValue().getId();
            if (catId.equalsIgnoreCase("car"))
                r.setCarInfo(carManager.getCarInfo());
            if (catId.equalsIgnoreCase("computer"))
                r.setComputerInfo(computerManager.getInfo());
            if (catId.equalsIgnoreCase("mobile"))
                r.setMobileInfo(mobileManager.getMobileInfo());
        }
        setTags(r, success1 -> RealestateRepository.addRealestate(r, (s) -> {
            success.setValue(s);
            ImageBuffer.reset();
            Log.e("add", "addRealestate: " + s);
        }));
    }


    private void setTags(Realestate r, CompleteListener listener) {
        List<String> words = Realestate.getWords(r);
        SearchUtils.getTagIds(words, (success1, data1) -> {
            if (data1 != null) {
                HashMap<String, Boolean> map = new HashMap<>();
                for (String id : data1)
                    map.put(id, true);
                r.setTags(map);
                Log.e("tags", "tags size " + map.size());
            } else
                Log.e("tags", "tags , no tags data null ");
            listener.onComplete(true);
        });
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isDataMissing() {
        Realestate r = realestate.getValue();

        return category.getValue() == null || isEmpty(price.getValue()) || isEmpty(r.getText()) || isEmpty(r.getTitle());
    }
}
