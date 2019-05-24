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

import com.minipoly.android.R;
import com.minipoly.android.adapter.RealestateAdapter;
import com.minipoly.android.utils.ZoomOutPageTransformer;

public class HomeFragment extends Fragment {

    private HomeViewModel model;
    private RealestateAdapter adapter;
    private ViewPager pager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
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
            pager = getView().findViewById(R.id.pager);
            pager.setOffscreenPageLimit(5);
            int w = pager.getWidth();
            pager = getView().findViewById(R.id.pager);
            // pager.setPadding(w/10,0,w/10,0);
            // pager.setPageMargin(-w/4);
            // pager.setClipToPadding(false);
            pager.setPageTransformer(false, new ZoomOutPageTransformer());
            pager.setAdapter(adapter);

        });
    }

}
