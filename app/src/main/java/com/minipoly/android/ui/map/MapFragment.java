package com.minipoly.android.ui.map;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.minipoly.android.R;
import com.minipoly.android.adapter.CategoryAdapter;
import com.minipoly.android.databinding.MapFragmentBinding;
import com.minipoly.android.utils.CardBuilder;

import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel model;
    private MapFragmentBinding binding;
    private CategoryAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.map_fragment,container,false);
        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(MapViewModel.class);
        binding.setVm(model);
        binding.setLifecycleOwner(this);
        binding.map.onCreate(savedInstanceState);
        model.setCardBuilder(new CardBuilder(getLayoutInflater()));
        binding.map.getMapAsync(this);
        prepareAdapter();
        model.categories.observe(this, categories -> adapter.submitList(categories));
    }


    private void prepareAdapter() {
        adapter = new CategoryAdapter();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.lstCategory.setLayoutManager(manager);
        binding.lstCategory.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        model.setMapAndGeocoder(googleMap, new Geocoder(getContext(), Locale.getDefault()));
        googleMap.setOnCameraIdleListener(() -> model.update());
        googleMap.setOnMapLongClickListener(latLng ->{
            binding.setLatlng(latLng);
            model.showMenu();
        });
        googleMap.setOnMapClickListener(latLng -> model.hideMenu());
         model.getCountries().observe(this,countries -> model.drawCountries(countries));
        model.getRealestates().observe(this, realestates -> model.drawRealestates(realestates));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (binding!=null)
            binding.map.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding!=null)
            binding.map.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (binding!=null)
            binding.map.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding!=null)
            binding.map.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (binding!=null)
            binding.map.onStop();
    }

}
