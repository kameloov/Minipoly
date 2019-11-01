package com.minipoly.android.ui.filters.realestate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.minipoly.android.adapter.CategoryAdapter;
import com.minipoly.android.databinding.RealestateFilterFragmentBinding;
import com.minipoly.android.filters.RealestateFilter;

public class RealestateFilterFragment extends Fragment {

    private RealestateFilterViewModel model;
    private RealestateFilterFragmentBinding binding;
    private CategoryAdapter adapter;
    private MapView mapView;

    public static RealestateFilterFragment newInstance() {
        return new RealestateFilterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RealestateFilterFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RealestateFilter filter = RealestateFilterFragmentArgs.fromBundle(getArguments()).getFilter();
        model = ViewModelProviders.of(this, new RealestateFilterFactory(filter)).get(RealestateFilterViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        prepareMap(mapView);
        prepareAdapter();
    }


    private void prepareAdapter() {
        adapter = new CategoryAdapter(category -> model.setCategory(category));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.lstCategory.setLayoutManager(manager);
        binding.lstCategory.setAdapter(adapter);
    }

    private void prepareMap(MapView mapView) {
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            mapboxMap.addOnMoveListener(new MapboxMap.OnMoveListener() {
                @Override
                public void onMoveBegin(@NonNull MoveGestureDetector detector) {

                }

                @Override
                public void onMove(@NonNull MoveGestureDetector detector) {

                }

                @Override
                public void onMoveEnd(@NonNull MoveGestureDetector detector) {
                    Log.e("map", "idle");
                    LayoutInflater inflater = getLayoutInflater();
                    model.updateRegion(mapboxMap.getProjection().getVisibleRegion().latLngBounds, inflater);
                }
            });
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
