package com.minipoly.android;

import androidx.multidex.MultiDexApplication;

import com.google.android.libraries.places.api.Places;

public class MinipolyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpService.init(this);
        Places.initialize(this, getString(R.string.google_api_key));
        UserManager.init(this);
    }
}
