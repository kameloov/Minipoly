package com.minipoly.android.ui.bars.top_bar;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.minipoly.android.NavGraphDirections;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.repository.UserRepository;

public class TopBarController {

    public MutableLiveData<Boolean> visible = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> open = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> menuOpen = new MutableLiveData<>(false);
    public UserBrief brief = UserRepository.getBrief();
    public MutableLiveData<Boolean> blink = new MutableLiveData<>(false);


    public void show() {

    }

    public void hide() {

    }

    public void showProfile(View v) {
        NavGraphDirections.ActionGlobalProfile profile = NavGraphDirections.actionGlobalProfile(UserRepository.getUserId());
        Navigation.findNavController(v).navigate(profile);
    }

    public void blink() {
        blink.setValue(true);
    }

    public void openOrShowProfile(View v) {
        if (open.getValue())
            showProfile(v);
        else
            open.setValue(true);
    }

    public void toggle() {
        boolean value = this.open.getValue();
        open.setValue(!value);
    }

    public void toggleMenu() {
        boolean value = this.menuOpen.getValue();
        blink.setValue(false);
        menuOpen.setValue(!value);
    }
}
