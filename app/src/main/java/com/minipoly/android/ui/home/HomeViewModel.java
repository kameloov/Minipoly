package com.minipoly.android.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Realestate;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.RealestateRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Realestate>> realestates = new MutableLiveData<>();
    private FireLiveQuery<Realestate> r;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<List<Realestate>> getRealestates() {
        return realestates;
    }

    public void load() {
        RealestateRepository.getRealestates((success, data) -> {
            if (success && data != null)
                realestates.postValue(data);
            Log.e("get realestates", "load: " + data.size());
        });
    }
}
