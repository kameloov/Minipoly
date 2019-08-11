package com.minipoly.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.minipoly.android.databinding.MainActivityBinding;
import com.minipoly.android.livedata.FireLiveUpload;
import com.minipoly.android.repository.MediaRepository;
import com.minipoly.android.repository.RealestateRepository;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    private ActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        model = ViewModelProviders.of(this).get(ActivityViewModel.class);
        model.setNavController(Navigation.findNavController(this, R.id.fragment));
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        handleLink();
    }

    private void handleLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                        Log.e("Link from activity ", deepLink.getPath());
                    }
                    if (deepLink != null) {
                        List<String> segments = deepLink.getPathSegments();
                        if (segments.get(0).equals("1"))
                            openRealestate(segments.get(1));
                    }
                })
                .addOnFailureListener(this, e -> Log.e("LINK", "getDynamicLink:onFailure", e));
    }

    private void openRealestate(String id) {
        RealestateRepository.getRealestate(id, (success, data) -> {
            if (success && data != null) {
                NavGraphDirections.ActionGlobalRealestateDetails action = NavGraphDirections.actionGlobalRealestateDetails(data);
                Navigation.findNavController(this, R.id.fragment).navigate(action);
            } else
                Toast.makeText(this, getString(R.string.realestate_not_found), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == UserManager.GOOGLE_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with FireBase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                UserManager.linkToGoogle(account, success -> {
                    if (success) {
                        uploadAvater(account.getPhotoUrl().getPath());
                        model.registerGmail(account);
                    } else
                        Toast.makeText(this, "خطأ ، لم يتم تسجيل الدخول", Toast.LENGTH_SHORT).show();
                });
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("google_login", "Google sign in failed", e);
                Toast.makeText(this, "خطأ ، لم يتم تسجيل الدخول", Toast.LENGTH_SHORT).show();
                // ...
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uploadAvater(String url) {
        new Thread(() -> {
            Glide.with(this).asBitmap().load(url).into(
                    new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (resource != null) {
                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, out);
                                MediaRepository.updateAvatar(UserManager.getUserID(), out.toByteArray(), new FireLiveUpload());
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }).start();
    }
}
