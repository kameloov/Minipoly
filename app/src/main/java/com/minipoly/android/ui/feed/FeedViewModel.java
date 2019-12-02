package com.minipoly.android.ui.feed;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.minipoly.android.ActivityViewModel;
import com.minipoly.android.NavGraphDirections;
import com.minipoly.android.R;
import com.minipoly.android.UserManager;
import com.minipoly.android.entity.Notification;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.filters.AdvrtFilter;
import com.minipoly.android.filters.FilterManager;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.popup.PopupNew;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.ImageBuffer;
import com.minipoly.android.utils.LocalData;
import com.minipoly.android.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;

public class FeedViewModel extends ViewModel implements ActivityViewModel.IKindListener {
    public FireLiveQuery<Notification> notifications = UserRepository.getMyNotifications(20);
    //todo remove temp notifications
    public MutableLiveData<List<Notification>> temp = new MutableLiveData<>();
    private FilterManager followFilterManager = new FilterManager();
    public MutableLiveData<List<Realestate>> follow = followFilterManager.data;
    public MutableLiveData<UserBrief> user = new MutableLiveData<>(new UserBrief());
    private FilterManager discoverFilterManager = new FilterManager();
    public MutableLiveData<List<Realestate>> near = discoverFilterManager.data;
    private AdvrtFilter followFilter = new AdvrtFilter();
    private AdvrtFilter discoverFilter = new AdvrtFilter();
    private boolean kind = false;

    public FeedViewModel() {
        ActivityViewModel.addKindListener(this);
        ActivityViewModel.addWorker(discoverFilterManager.loading);
        ActivityViewModel.addWorker(followFilterManager.loading);
        getDiscover();
        getFollow();
        user.setValue(UserRepository.getBrief());
    }

    private void getDiscover() {
        LatLng loction = LocalData.getLocation();
        if (loction != null) {
            discoverFilter.setSquare(MapUtils.getCoordinatesString(6, loction));
            discoverFilterManager.filter(discoverFilter.getFilters());
        }

    }

    public void showNew(View view) {
        PopupNew popupNew = new PopupNew(view.getContext(), type -> {
            NavController navController = Navigation.findNavController(view);
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

    public void showProfile(View view) {
        NavGraphDirections.ActionGlobalProfile profile =
                NavGraphDirections.actionGlobalProfile(UserManager.getUserID());
        Navigation.findNavController(view).navigate(profile);
    }

    public void setTempNotifications() {
        List<Realestate> lst = near.getValue();
        ArrayList<Notification> notifications = new ArrayList<>();
        if (lst != null) {
            for (Realestate r : lst) {
                Notification n = new Notification();
                n.setId(r.getId());
                n.setUser(r.getUserBrief());
                n.setText(r.getText());
                n.setTimeStamp(r.getPublishDate());
                notifications.add(n);
            }
            temp.setValue(notifications);
        }
    }

    public void loadMoreDiscover() {
        discoverFilterManager.loadMore();
    }

    public void loadMoreFollow() {
        followFilterManager.loadMore();
    }

    private void getFollow() {
        RealestateRepository.getFollowingAds((success, data) -> {
            if (success && data != null)
                follow.setValue(data);
        });
    }

    public void showHome(View v) {
        Navigation.findNavController(v).navigate(FeedFragmentDirections.actionFeedFragmentToHomeFragment());
    }

    @Override
    public void onKindChanged(boolean kind) {
        this.kind = !kind;
        discoverFilter.setMarket(!kind);
        followFilter.setMarket(!kind);
        getDiscover();
    }


}
