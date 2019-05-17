package com.minipoly.android.ui.card_dialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Realestate;

public class RealestateCardViewModel extends ViewModel {
    private MutableLiveData<Realestate> realestate = new MutableLiveData<>();

    public LiveData<Realestate> getRealestate() {
        return realestate;
    }

    public void setRealestate(Realestate r) {
        this.realestate.setValue(r);
    }
}
