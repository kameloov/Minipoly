package com.minipoly.android.ui.realestate_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Realestate;

public class RealestateDetailsViewModel extends ViewModel {
    private MutableLiveData<Realestate> relestate = new MutableLiveData<>();

    public void setRelestate(Realestate relestate) {
        this.relestate.setValue(relestate);
    }

    public LiveData<Realestate> getRealestate() {
        return relestate;
    }
}
