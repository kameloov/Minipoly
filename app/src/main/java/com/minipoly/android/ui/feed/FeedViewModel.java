package com.minipoly.android.ui.feed;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.minipoly.android.ActivityViewModel;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.filters.AdvrtFilter;
import com.minipoly.android.filters.FilterManager;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.LocalData;
import com.minipoly.android.utils.MapUtils;

import java.util.List;

public class FeedViewModel extends ViewModel implements ActivityViewModel.IKindListener {
    private FilterManager nearFilterManager = new FilterManager();
    public MutableLiveData<List<Realestate>> near = nearFilterManager.data;
    private FilterManager followFilterManager = new FilterManager();
    public MutableLiveData<List<Realestate>> follow = followFilterManager.data;
    private AdvrtFilter nearFilter = new AdvrtFilter();
    private AdvrtFilter followFilter = new AdvrtFilter();
    private boolean kind = false;

    public FeedViewModel() {
        ActivityViewModel.addKindListener(this);
        getNear();
    }

    private void getNear() {
        LatLng loction = LocalData.getLocation();
        if (loction != null) {
            nearFilter.setSquare(MapUtils.getCoordinatesString(6, loction));
            nearFilterManager.filter(nearFilter.getFilters());
        }

    }

    private void getFollow() {
        SocialRepository.getFollowers(UserRepository.getUserId(), (success, data) -> {
            if (success && data != null) {

            }
        });
    }

    @Override
    public void onKindChanged(boolean kind) {
        this.kind = !kind;
        nearFilter.setMarket(!kind);
    }
}
