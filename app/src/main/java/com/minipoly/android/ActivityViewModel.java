package com.minipoly.android;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.minipoly.android.entity.User;
import com.minipoly.android.popup.PopupNew;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.ImageBuffer;
import com.minipoly.android.utils.LocalData;

public class ActivityViewModel extends ViewModel {

    private MutableLiveData<Integer> section = new MutableLiveData<>();
    private MutableLiveData<Boolean> sectionsVisible = new MutableLiveData<>();
    private NavController navController;

    public LiveData<Integer> getSection() {
        return section;
    }

    public LiveData<Boolean> getSectionViible() {
        return sectionsVisible;
    }

    public void setSection(View v, int section) {
        this.section.setValue(section);
        refresh(v);

    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public void showSections() {
        sectionsVisible.setValue(true);
    }

    public void hideSections() {
        sectionsVisible.setValue(false);
    }

    public void registerGmail(GoogleSignInAccount account) {
        User user = new User();
        user.setId(UserManager.getUserID());
        user.setName(account.getGivenName());
        user.setToken(LocalData.getDeviceToken());
        UserRepository.addUser(user, success -> {
        });
    }


    public void showNew(View view) {
        section.setValue(-1);
        PopupNew popupNew = new PopupNew(view.getContext(), type -> {
            switch (type) {
                case NT_AD:
                    ImageBuffer.reset();
                    navController.navigate(R.id.action_global_add_promoted);
                    break;
                case NT_AUCTION:
                    ImageBuffer.reset();
                    NavGraphDirections.ActionGlobalAddAuction
                            auction = NavGraphDirections.actionGlobalAddAuction(AuctionRepository.generateAuction(12, 11, null));
                    navController.navigate(auction);
                    break;
                case NT_DEAL:
                    ImageBuffer.reset();
                    NavGraphDirections.ActionGlobalAddRealestate
                            realestate = NavGraphDirections.actionGlobalAddRealestate(RealestateRepository.generateRealestate(5, -7, null));
                    navController.navigate(realestate);
                    break;
            }
        });
        Log.e("showNew: ", "id is " + view.getId());
        popupNew.show(view);
    }

    private void refresh(View v) {
        int index = section.getValue();
        switch (index) {
            case 0:
                navController.navigate(R.id.homeFragment);
                break;
            case 2:
                navController.navigate(R.id.mapFragment);
                break;
            case -2:
                navController.navigate(R.id.editProfileFragment);
                break;


        }
    }
}
