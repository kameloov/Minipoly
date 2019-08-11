package com.minipoly.android.ui.profile;

import android.util.Log;

import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.PieEntry;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.User;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    public FireLiveDocument<User> user;
    public CustomRadio radio = new CustomRadio(true, "About", "Deals");
    public MutableLiveData<List<Realestate>> ads = new MutableLiveData<>();
    public MutableLiveData<List<PieEntry>> chartData = new MutableLiveData<>();
    public MutableLiveData<Boolean> about = new MutableLiveData<>(true);

    public ProfileViewModel(String userId) {
        radio.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.e("onPropertyChanged: ", "property changed");
                if (radio.isChecked())
                    showAbout();
                else
                    showDeals();

            }
        });
        user = UserRepository.getUser(userId);
        RealestateRepository.getUserRealestates(userId, (success, data) -> {
            if (success && data != null) {
                ads.setValue(data);
                fillChart(data);
            }
        });
    }

    private void fillChart(List<Realestate> ads) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        int sale = 0;
        int rent = 0;
        int rented = 0;
        for (Realestate r : ads) {
            if (!r.isMarket()) {
                if (r.getRealestateInfo().isRent()) {
                    if (r.isRented())
                        rented++;
                    else
                        rent++;
                } else
                    sale++;
            }
        }
        if (sale > 0)
            entries.add(new PieEntry(sale, "Sale"));
        if (rent > 0)
            entries.add(new PieEntry(rent, "rent"));
        if (rented > 0)
            entries.add(new PieEntry(rented, "rented"));
        chartData.setValue(entries);
    }

    public void showAbout() {
        about.setValue(true);
    }

    public void showDeals() {
        about.setValue(false);
    }
}
