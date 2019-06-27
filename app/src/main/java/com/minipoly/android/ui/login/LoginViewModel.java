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
    private MutableLiveData<Boolean> registered = new MutableLiveData<>();

    public LoginViewModel() {
        User u = new User();
        user.setValue(u);
    }

    public LiveData<Boolean> isRegistered() {
        return registered;
    }

    public void register() {
        UserManager.loginAnonymously(success -> {
            if (success) {
                User u = user.getValue();
                u.setId(UserManager.getUserID());
                SocialRepository.addUser(u, success1 -> {
                    registered.setValue(success1);
                    LocalData.saveUserInfo(u);
                });
            }
        });
    }

}
