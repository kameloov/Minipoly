package com.minipoly.android.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.minipoly.android.adapter.RealestateAdapter;
import com.minipoly.android.databinding.UserDealsFragmentBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.utils.ZoomOutPageTransformer;

import java.util.List;

public class UserDealsFragment extends Fragment {

    private UserDealsViewModel model;
    private UserDealsFragmentBinding binding;
    private String userId;
    private List<Realestate> ads;

    public static UserDealsFragment newInstance(String userId, List<Realestate> ads) {
        UserDealsFragment fragment = new UserDealsFragment();
        fragment.userId = userId;
        fragment.ads = ads;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = UserDealsFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, new UserDealsViewModelFactory(userId, ads)).get(UserDealsViewModel.class);
        binding.setLifecycleOwner(this);
        preparePager();
        attachObservers();
    }

    private void preparePager() {
        binding.pager.setOffscreenPageLimit(5);
        binding.pager.setPageTransformer(false, new ZoomOutPageTransformer());
    }

    private void attachObservers() {
        model.ads.observe(this, realestates -> {
            RealestateAdapter adapter = new RealestateAdapter(realestates);
            binding.pager.setAdapter(adapter);
            Log.e("attachObservers: ", "got new Ads count is " + realestates.size());
        });
    }

}
