package com.minipoly.android.ui.newadvrt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Realestate;
import com.minipoly.android.repository.RealestateRepository;

public class AddAdvrtDialogViewModel extends ViewModel {

    private MutableLiveData<Realestate> realestate = new MutableLiveData<>();

    public LiveData<Realestate> getRealestate() {
        return realestate;
    }

    public void setRealestate(Realestate realestate) {
        this.realestate.setValue( realestate);
        RealestateRepository.addRealestate(realestate);
    }
}
