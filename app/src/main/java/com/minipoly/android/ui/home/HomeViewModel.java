package com.minipoly.android.ui.home;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.minipoly.android.entity.Realestate;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.AuctionRepository;
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

    public void showAuctionDetails(View view) {
        AuctionRepository.getAuctions((success, data) -> {
            showAuctionEnd(view);

  /*          if (success){
                HomeFragmentDirections.ActionHomeFragmentToAuctionDetails action =
                        HomeFragmentDirections.actionHomeFragmentToAuctionDetails(data.get(0));
                Navigation.findNavController(view).navigate(action);
            }*/
        });
    }


    public void showAuctionEnd(View view) {
        AuctionRepository.getAuctions((success, data) -> {
            if (success) {
                HomeFragmentDirections.AuctionEnd action =
                        HomeFragmentDirections.auctionEnd(data.get(0).getId());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    public void showEditProfile(View view) {
        AuctionRepository.getAuctions((success, data) -> {
            if (success) {
                Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToEditProfileFragment());
            }
        });
    }
}
