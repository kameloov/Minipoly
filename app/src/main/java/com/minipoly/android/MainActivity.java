package com.minipoly.android;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.minipoly.android.databinding.MainActivityBinding;
import com.minipoly.android.repository.RealestateRepository;

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
}
