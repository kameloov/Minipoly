package com.minipoly.android;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.minipoly.android.entity.User;
import com.minipoly.android.repository.UserRepository;
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

    private void refresh(View v) {
        int index = section.getValue();
        switch (index) {
            case 0:
                navController.navigate(R.id.homeFragment);
                break;
            case 1:
                navController.navigate(R.id.mapFragment);
                break;
            case -1:
                navController.navigate(R.id.editProfileFragment);
                break;


        }
    }
}
