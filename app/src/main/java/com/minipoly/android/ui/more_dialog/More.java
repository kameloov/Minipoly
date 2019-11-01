package com.minipoly.android.ui.more_dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.Style;
import com.minipoly.android.R;
import com.minipoly.android.databinding.MoreFragmentBinding;
import com.minipoly.android.entity.Realestate;

public class More extends DialogFragment {

    private MoreViewModel model;
    private MoreFragmentBinding binding;

    public static More newInstance() {
        return new More();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_Dialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding = MoreFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Realestate realestate = MoreArgs.fromBundle(getArguments()).getRealestate();
        model = ViewModelProviders.of(this, new MoreModelFactory(realestate)).get(MoreViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_token));
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
         /*   SymbolManager manager = new SymbolManager(binding.mapView,mapboxMap,style);
            FeatureCollection collection = FeatureCollection.fromFeature(Feature.fromGeometry(
                    Point.fromLngLat(realestate.getLat(), realestate.getLang())));
            manager.create(collection);*/

            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(realestate.getLat(), realestate.getLang()))
                    .zoom(5)
                    .build();
            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));

        }));
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }


}
