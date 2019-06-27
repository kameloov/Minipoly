package com.minipoly.android.ui.popup;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class PopUpViewModel extends ViewModel {

    private static final String TAG = "PopUpViewModel";

    public void addRealestate(LatLng latLng){
        Log.e(TAG, "addRealestate: add realestate clikced with lat lang "+ latLng.latitude+","+ latLng.longitude);
    }
}
