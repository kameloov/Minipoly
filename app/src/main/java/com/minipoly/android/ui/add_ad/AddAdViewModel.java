package com.minipoly.android.ui.add_ad;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.PromotedAd;
import com.minipoly.android.repository.PromotedAdsRepository;
import com.minipoly.android.utils.ImageBuffer;

import java.util.List;

public class AddAdViewModel extends ViewModel {

    public MutableLiveData<PromotedAd> ad = new MutableLiveData<>(new PromotedAd());
    public MutableLiveData<Country> country = new MutableLiveData<>();
    public MutableLiveData<Command> command = new MutableLiveData<>(Command.IDLE);
    public CustomRadio contactType = new CustomRadio(false, "", "");
    public LiveData<List<Image>> images = ImageBuffer.getImages();

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isDataMissing() {
        PromotedAd pa = ad.getValue();
        return ad.getValue() == null || isEmpty(pa.getTitle()) || isEmpty(pa.getText()) || isEmpty(pa.getCountryId());
    }

    public void selectCountry() {
        command.setValue(Command.SHOW_LOCATION);
    }

    public void addAd() {
        if (isDataMissing()) {
            command.setValue(Command.DATA_MISSING);
            return;
        }

        PromotedAd promotedAd = ad.getValue();
        promotedAd.setImages(images.getValue());
        promotedAd.setImages(ImageBuffer.getImages().getValue());
        PromotedAdsRepository.addPromotedAd(promotedAd, success -> command.setValue(
                success ? Command.SUCCESS : Command.FAILED));
    }


    public void setCountry(Country country) {
        this.country.setValue(country);
        PromotedAd current = ad.getValue();
        current.setCountryId(country.getId());
        current.setCountryName(current.getCountryName());
        current.setCountryNameAr(current.getCountryNameAr());
        ad.setValue(current);
    }


    public enum Command {IDLE, SUCCESS, FAILED, SHOW_LOCATION, DATA_MISSING}
}
