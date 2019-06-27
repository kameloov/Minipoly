package com.minipoly.android.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.minipoly.android.adapter.RealestateAdapter;
import com.minipoly.android.databinding.HomeFragmentBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.utils.ZoomOutPageTransformer;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel model;
    private RealestateAdapter adapter;
    private ViewPager pager;
    private HomeFragmentBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(HomeViewModel.class);
        model.load();
        model.getRealestates().observe(this, realestates -> {
            if (adapter == null)
                adapter = new RealestateAdapter(realestates, getContext());
            else
                adapter.setRealestates(realestates);
            preparePager(binding.pager, realestates);

        });
    }

    private void preparePager(ViewPager pager, List<Realestate> realestates) {
        pager.setOffscreenPageLimit(5);
        pager.setPageTransformer(false, new ZoomOutPageTransformer());
        pager.setAdapter(adapter);
    }

}
