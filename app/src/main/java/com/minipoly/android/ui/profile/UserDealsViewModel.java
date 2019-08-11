package com.minipoly.android.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Realestate;

import java.util.List;

public class UserDealsViewModel extends ViewModel {

    public MutableLiveData<List<Realestate>> ads = new MutableLiveData<>();
    private String userId;

    public UserDealsViewModel(String userId, List<Realestate> ads) {
        this.userId = userId;
        this.ads.setValue(ads);
       /* RealestateRepository.getUserRealestates(userId,(success, data) -> {
            if (success && data!=null)
                ads.setValue(data);
        });*/
    }
}
