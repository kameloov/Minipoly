package com.minipoly.android;

import android.view.View;

import androidx.databinding.Observable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.User;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.LocalData;

import java.util.ArrayList;

public class ActivityViewModel extends ViewModel {

    private MutableLiveData<Integer> section = new MutableLiveData<>();
    private MutableLiveData<Boolean> sectionsVisible = new MutableLiveData<>();
    public static CustomRadio kindRadio = new CustomRadio(true, "Realestate", "Market");
    private static ArrayList<IKindListener> listeners = new ArrayList<>();
    private static NavController navController;
    public static int activeWorkers = 0;
    private static MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private static MutableLiveData<Boolean> homeMode = new MutableLiveData<>(false);
    private static MediatorLiveData<Boolean> working = new MediatorLiveData<>();
    public ActivityViewModel() {

        kindRadio.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                notifyListeners(kindRadio.isChecked());
            }
        });
    }

    public static LiveData<Boolean> getLoading() {
        return loading;
    }

    public static void setLoading(boolean status) {
        loading.setValue(status);
    }

    public static LiveData<Boolean> getHomeMode() {
        return homeMode;
    }

    public static void setHomeMode(boolean home) {
        homeMode.setValue(home);
    }

    public static void addWorker(LiveData<Boolean> worker) {
        working.addSource(worker, b -> {
            if (b)
                activeWorkers++;
            else
                activeWorkers--;
            working.setValue(activeWorkers > 0);
        });
    }

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

    public static void removeWorker(LiveData<Boolean> worker) {
        working.removeSource(worker);
    }

    public LiveData<Boolean> getWorking() {
        return working;
    }

    public void toggleMode() {
        kindRadio.setChecked(!kindRadio.isChecked());
    }

    public static NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        ActivityViewModel.navController = navController;
    }

    public static void removeKindListener(IKindListener listener) {
        listeners.remove(listener);
    }

    private static void notifyListeners(boolean value) {
        for (IKindListener listener : listeners) {
            if (listener != null)
                listener.onKindChanged(value);
        }
    }

    public static void addKindListener(IKindListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
        listener.onKindChanged(kindRadio.isChecked());
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
        UserRepository.addUser(user, success -> LocalData.saveUserInfo(user));
    }

    private void refresh(View v) {
        int index = section.getValue();
        switch (index) {
            case 0:
                navController.navigate(R.id.homeFragment);
                break;
            case 2:
                navController.navigate(R.id.auctionsFragment);
                break;
            case -2:
                // navController.navigate(R.id.editProfileFragment);
                break;


        }
    }

    public interface IKindListener {
        void onKindChanged(boolean kind);
    }
}
