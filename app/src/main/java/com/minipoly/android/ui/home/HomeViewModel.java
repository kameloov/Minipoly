package com.minipoly.android.ui.home;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.minipoly.android.entity.Notification;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.ui.top_bar.TopBarController;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Realestate>> realestates = new MutableLiveData<>();
    private FireLiveQuery<Realestate> r;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public TopBarController barController = new TopBarController();
    public FireLiveQuery<Notification> notifications = UserRepository.getUserNotifications(UserRepository.getUserId(), 10);

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

    public void hideBar() {
        barController.toggle();
        barController.toggleMenu();
    }

    public void showAuctionDetails(View view) {
        AuctionRepository.getAuctions((success, data) -> {
            if (success) {
                HomeFragmentDirections.ActionHomeFragmentToAuctionDetails action =
                        HomeFragmentDirections.actionHomeFragmentToAuctionDetails(data.get(0));
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    public void showAuction(String id, NavController controller) {
        AuctionRepository.getAuction(id, (success, data) -> {
            if (success && data != null) {
                HomeFragmentDirections.ActionHomeFragmentToAuctionDetails action =
                        HomeFragmentDirections.actionHomeFragmentToAuctionDetails(data);
                controller.navigate(action);
            }
        });
    }

    public void showRealestate(String id, NavController controller) {
        RealestateRepository.getRealestate(id, (success, data) -> {
            if (success && data != null) {
                HomeFragmentDirections.RealestateDetails realestateDetails =
                        HomeFragmentDirections.realestateDetails(data);
                controller.navigate(realestateDetails);
            }
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
