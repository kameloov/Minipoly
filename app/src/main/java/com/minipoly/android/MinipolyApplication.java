package com.minipoly.android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

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
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id), name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
