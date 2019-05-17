package com.minipoly.android.ui.map;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.minipoly.android.R;
import com.minipoly.android.databinding.MapFragmentBinding;
import com.minipoly.android.utils.CardBuilder;

import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel model;
    private MapFragmentBinding binding;

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
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        model.setMapAndGeocoder(googleMap, new Geocoder(getContext(), Locale.getDefault()));
        googleMap.setOnCameraIdleListener(() -> model.setRegion(googleMap.getProjection().getVisibleRegion()));
        googleMap.setOnMapLongClickListener(latLng ->{
            binding.setLatlng(latLng);
            model.showMenu();
        });
        googleMap.setOnMapClickListener(latLng -> {
            model.hideMenu();
            //model.goToCountry(latLng);
        });

         model.getCountries().observe(this,countries -> model.drawCountries(countries));
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
