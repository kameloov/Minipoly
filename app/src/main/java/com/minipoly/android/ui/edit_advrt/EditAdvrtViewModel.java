package com.minipoly.android.ui.edit_advrt;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Realestate;
import com.minipoly.android.popup.PopupDiscount;
import com.minipoly.android.popup.PopupPromote;
import com.minipoly.android.repository.RealestateRepository;

import java.util.Calendar;
import java.util.Date;

public class EditAdvrtViewModel extends ViewModel {

    public Realestate realestate;
    public MutableLiveData<Boolean> discount = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> rented = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> promoted = new MutableLiveData<>(false);
    public MutableLiveData<String> message = new MutableLiveData<>("");


    public EditAdvrtViewModel(Realestate realestate) {
        this.realestate = realestate;
        this.rented.setValue(realestate.isRented());
        this.promoted.setValue(isPromoted());
        this.discount.setValue(isOffer());
    }

    public void setDiscount(View v) {
        if (!discount.getValue())
            discount(v);
        else
            RealestateRepository.setDiscount(realestate.getId(), 0, realestate.getPublishDate(), success -> {
                if (success) {
                    discount.setValue(false);
                    message.setValue("Discount has been disabled");
                }
            });
    }

    public void setPromote(View v) {
        if (!promoted.getValue()) {
            PopupPromote promote = new PopupPromote(v, days -> {
                RealestateRepository.promote(realestate.getId(), days);
                promoted.setValue(true);
            });
            promote.show();
        }
    }

    private void discount(View v) {
        PopupDiscount discount = new PopupDiscount(v, (days, percent) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, days);
            Date end = calendar.getTime();
            RealestateRepository.setDiscount(realestate.getId(), percent, end, success -> {
                message.setValue("Added discount successfully");
                if (success)
                    this.discount.setValue(true);
            });
        });
        discount.show();
    }

    private boolean isOffer() {
        if (realestate.getOfferEnd() == null)
            return false;
        Calendar calendar = Calendar.getInstance();
        return !realestate.getOfferEnd().before(calendar.getTime());
    }

    private boolean isPromoted() {
        if (realestate.getPromoteEnd() == null)
            return false;
        Calendar calendar = Calendar.getInstance();
        return !realestate.getPromoteEnd().before(calendar.getTime());
    }

    public void setRented(boolean value) {
        RealestateRepository.setRented(realestate.getId(), value, success -> {
            if (success) {
                rented.setValue(value);
                message.setValue("Rent state changed successfully");
            } else
                message.setValue("Error, Failed to change value ");
        });
    }
}
