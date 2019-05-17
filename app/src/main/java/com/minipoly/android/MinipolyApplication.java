package com.minipoly.android;

import androidx.multidex.MultiDexApplication;

public class MinipolyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpService.init(this);
    }
}
