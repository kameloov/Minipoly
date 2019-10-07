package com.minipoly.android.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.UserManager;
import com.minipoly.android.entity.User;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.LocalData;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private LiveData<Boolean> registered = UserManager.getLoginState();

    public LoginViewModel() {
        User u = new User();
        user.setValue(u);
    }

    public LiveData<Boolean> isRegistered() {
        return registered;
    }

    public void register() {
        this.loading.setValue(true);
        UserManager.loginAnonymously(success -> {
            if (success) {
                User u = user.getValue();
                u.setId(UserManager.getUserID());
                u.setToken(LocalData.getDeviceToken());
                SocialRepository.addUser(u, success1 -> LocalData.saveUserInfo(u));
                this.loading.setValue(false);
            }
        });
    }


}
