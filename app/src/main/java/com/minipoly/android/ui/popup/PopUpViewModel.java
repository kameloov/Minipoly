package com.minipoly.android.ui.popup;

import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PopUpViewModel extends ViewModel {

    private static final String TAG = "PopUpViewModel";

    public void addRealestate(LatLng latLng){
        Log.e(TAG, "addRealestate: add realestate clikced with lat lang "+ latLng.latitude+","+ latLng.longitude);
    }
}
