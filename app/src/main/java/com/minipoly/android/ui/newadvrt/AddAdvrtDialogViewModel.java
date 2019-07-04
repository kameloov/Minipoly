package com.minipoly.android.ui.newadvrt;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.UserManager;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.Uploader;

import java.util.List;

public class AddAdvrtDialogViewModel extends ViewModel {
    public enum Command {IDLE, SHOW_CATEGORY, SHOW_SUBCATEGORY}

    public MutableLiveData<CustomRadio> kindRadio = new MutableLiveData<>();
    public MutableLiveData<CustomRadio> typeRadio = new MutableLiveData<>();
    public MutableLiveData<CustomRadio> usedRadio = new MutableLiveData<>();
    private MutableLiveData<Realestate> realestate = new MutableLiveData<>();
    public MutableLiveData<Category> category = new MutableLiveData<>();
    public MutableLiveData<Category> subCategory = new MutableLiveData<>();
    public String[] currencies = new String[]{"USD", "EUR", "SAR", "EGP", "SYP"};
    public MutableLiveData<Integer> index = new MutableLiveData<>(0);
    private List<Image> images;
    private LiveData<Image> defaultImage;
    private static Uploader uploader;
    public MutableLiveData<String> price = new MutableLiveData<>();
    public MutableLiveData<String> area = new MutableLiveData<>();
    public MutableLiveData<Boolean> success = new MutableLiveData<>();
    public MutableLiveData<Boolean> extraVisible = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectedCategory = new MutableLiveData<>();
    public MobileManager mobileManager = new MobileManager();
    public MutableLiveData<Integer> old = new MutableLiveData<>(1);
    public RealestateManager realestateManager;
    public CarManager carManager = new CarManager();
    public ComputerManager computerManager = new ComputerManager();
    public MutableLiveData<Command> command = new MutableLiveData<>(Command.IDLE);

    public AddAdvrtDialogViewModel() {
        uploader = new Uploader(UserManager.getUserID());
        defaultImage = uploader.getDefaultImage();
        selectedCategory.setValue(0);
        realestateManager = new RealestateManager(new Realestate());
        kindRadio.setValue(new CustomRadio(false, "Add Realestate", "Add Market"));
        typeRadio.setValue(new CustomRadio(false, "For Rent", "For Sale"));
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

    public static Uploader getUploader() {
        return uploader;
    }

    public LiveData<Realestate> getRealestate() {
        return realestate;
    }

    public void changeRooms(int i) {
        Realestate r = realestate.getValue();
        r.setRoomCount(r.getRoomCount() + i);
        realestate.postValue(r);
    }

    public void showSelect(boolean subcategory) {
        command.setValue(subcategory ? Command.SHOW_SUBCATEGORY : Command.SHOW_CATEGORY);
    }


    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<Image> getDefaultImage() {
        return defaultImage;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setRealestate(Realestate realestate) {
        this.realestate.setValue( realestate);
        this.realestateManager = new RealestateManager(realestate);
    }

    public void addRealestate() {
        // todo missing values like category and subcategory
        Realestate r = realestate.getValue();
        r.setArea(Float.parseFloat(area.getValue()));
        r.setPrice(Float.parseFloat(price.getValue()));
        r.setUserBrief(SocialRepository.getDemoBrief());
        r.setRent(typeRadio.getValue().isChecked());
        r.setImages(uploader.getImages());
        RealestateRepository.addRealestate(r, (s) -> {
            success.setValue(s);
            Log.e("add", "addRealestate: " + s);
        });
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isDataMissing() {
        Realestate r = realestate.getValue();

        return isEmpty(price.getValue()) || isEmpty(area.getValue()) || isEmpty(r.getText()) || isEmpty(r.getTitle());
    }
}
