package com.minipoly.android.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.minipoly.android.R;
import com.minipoly.android.RootFragment;
import com.minipoly.android.adapter.NotificationAdapter;
import com.minipoly.android.adapter.RealestateAdapter1;
import com.minipoly.android.databinding.HomeFragmentBinding;
import com.minipoly.android.ui.category_dialog.CategoryDialog;

public class HomeFragment extends RootFragment {

    private HomeViewModel model;
    private RealestateAdapter1 adapter = new RealestateAdapter1();
    private HomeFragmentBinding binding;
    private NotificationAdapter notificationAdapter;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private MarkerViewManager markerViewManager;


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
        model = ViewModelProviders.of(this, new HomeViewModelFactory(binding.getRoot())).get(HomeViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        binding.load.setVisibility(View.INVISIBLE);
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        showNav();
        prepareMap();
        prepareAdapter();
        prepareNotificationAdapter();
        attachObservers();
    }


    private void prepareMap() {
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            this.mapboxMap = mapboxMap;
            markerViewManager = new MarkerViewManager(mapView, mapboxMap);
            model.setMarkerManger(markerViewManager);
            model.setSymbolManager(new SymbolManager(mapView, mapboxMap, style, null));

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

                    if (model.map.getValue())
                        model.updateRegion(mapboxMap.getProjection().getVisibleRegion().latLngBounds, inflater);
                }
            });
        }));
    }

    private void attachObservers() {
        model.command.observe(this, command -> {
            if (command == HomeViewModel.Command.IDLE)
                return;
            String id = command == HomeViewModel.Command.SELECT_CAT ?
                    null : model.category.getValue().getId();
            CategoryDialog dialog = CategoryDialog.newInstance(id, !model.kind.getValue(), (sub, category) -> model.setCatOrSubId(sub, category));

            dialog.show(getFragmentManager(), "CAT");
            model.command.setValue(HomeViewModel.Command.IDLE);
        });

        model.getRealestates().observe(this, realestates -> {
            binding.load.clearAnimation();
            model.clearMarkers();
            binding.load.setVisibility(View.INVISIBLE);
            adapter.submitList(realestates);
            model.addRealestateMarkers(getLayoutInflater());


        });

        model.notifications.observe(this, notifications -> {
            if (notifications != null) {
                notificationAdapter.submitList(notifications);
                model.barController.blink();
            }
        });

        model.kind.observe(this, aBoolean -> {
            playAnimation();
            model.realestateFilter.setMarket(aBoolean);
        });
    }

    private void playAnimation() {
        if (model.getLoading().getValue()) {
            Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            binding.load.startAnimation(a);
        }
    }


    private void prepareAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        binding.lstAds.setLayoutManager(manager);
        adapter.setListener(realestate -> {
            if (realestate != null) {
                hideNav();
                model.showRealestate(realestate, binding.lstAds);
            }
        });
        binding.lstAds.setAdapter(adapter);
    }

    private void prepareNotificationAdapter() {
        notificationAdapter = new NotificationAdapter(notification -> {
            if (notification != null) {
                NavController controller = Navigation.findNavController(binding.getRoot());
                int type = notification.getType();
                if (type == 1)
                    model.showRealestate(notification.getItemId(), controller);
                if (type == 2)
                    model.showAuction(notification.getItemId(), controller);
                model.hideBar();
            }
        });
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
        model.manager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }


}
