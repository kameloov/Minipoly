package com.minipoly.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;

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
        startMainActivity();
    }

    private void startMainActivity() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, DELAY_MILLIS);
    }

}
