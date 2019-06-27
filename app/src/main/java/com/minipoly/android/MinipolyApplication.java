package com.minipoly.android;

import androidx.multidex.MultiDexApplication;

import com.google.android.libraries.places.api.Places;
import com.minipoly.android.utils.LocalData;

public class MinipolyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpService.init(this);
        Places.initialize(this, getString(R.string.google_api_key));
        UserManager.init(this);
        LocalData.init(this);
    }
}
