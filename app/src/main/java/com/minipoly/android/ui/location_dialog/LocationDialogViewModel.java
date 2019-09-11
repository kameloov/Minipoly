package com.minipoly.android.ui.location_dialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Country;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.MiscRepository;

public class LocationDialogViewModel extends ViewModel {
    public MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    public MutableLiveData<String> title = new MutableLiveData<>("Countries");
    private String countryId;
    private FireLiveQuery<Country> countries;

    public LocationDialogViewModel(String countryId) {
        this.countryId = countryId;
        loading.setValue(true);
        if (countryId == null)
            countries = MiscRepository.getCountries();
        else {
            countries = MiscRepository.getCities(countryId);
            title.setValue("Cities");
        }
    }

    public FireLiveQuery<Country> getCountries() {
        return countries;
    }

}
