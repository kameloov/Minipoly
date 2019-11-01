package com.minipoly.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.User;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.LocalData;
import com.minipoly.android.utils.MapUtils;

import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private static final int DELAY_MILLIS = 2000;
    private String token = "pk.eyJ1Ijoia2FtZWxvb3YiLCJhIjoiY2swazh0endwMGl1dDNkcXMxcWZnODlnayJ9.5VCEVveo3r2ZnYlyyW9I7g";
    private final Handler mHideHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        UserManager.initGoogleSignIn(this);
        Mapbox.getInstance(getApplicationContext(), token);
        updateLocation();
        if (!UserManager.isLogged())
            login();
        else
            startMainActivity(DELAY_MILLIS);
    }

    private void login() {
        UserManager.loginAnonymously(success -> {
            if (success) {
                User u = new User();
                u.setName("Guest");
                u.setBio("Hello am new User");
                u.setId(UserManager.getUserID());
                u.setToken(LocalData.getDeviceToken());
                SocialRepository.addUser(u, success1 -> {
                    LocalData.saveUserInfo(u);
                    startMainActivity(0);
                });
            }
        });
    }

    private void updateLocation() {
        FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                double lat = task.getResult().getLatitude();
                double lng = task.getResult().getLongitude();
                LatLng latLng = new LatLng(lat, lng);
                LocalData.saveLocation(latLng);
            }
        });
    }

    private void fixRealestates(List<Realestate> realestates) {
        for (Realestate r : realestates) {
            LatLng latLng = new LatLng(r.getLat(), r.getLang());
            for (int i = 0; i < 10; i++) {
                r.setLevelCoord(i, MapUtils.getCoordinatesString(i, latLng));
                r.setCategoryName(r.getCategoryId());
            }
            RealestateRepository.setRealestate(r);
        }
    }

    private void startMainActivity(int wait) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, wait);
    }

}
