package com.minipoly.android.ui.more_dialog;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.SocialUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;


public class MoreViewModel extends ViewModel implements OnMapReadyCallback {

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    public Realestate realestate;
    public List<String> tags;
    public MutableLiveData<Boolean> callVisible = new MutableLiveData<>(false);

    public MoreViewModel(Realestate realestate) {
        this.realestate = realestate;
        prepareTags(realestate);
    }

    public void toggleVisible() {
        callVisible.setValue(!callVisible.getValue());
    }

    private void prepareTags(Realestate r) {
        if (!r.isMarket())
            tags = RealestateManager.getTags(r.getRealestateInfo());
        else {
            if (r.getCategoryId().equals("car"))
                tags = CarManager.getTags(r.getCarInfo());
            if (r.getCategoryId().equals("computer"))
                tags = ComputerManager.getTags(r.getComputerInfo());
            if (r.getCategoryId().equals("mobile"))
                tags = MobileManager.getTags(r.getMobileInfo());
        }
    }

    public void orderwhats(View v) {
        String name = UserRepository.getBrief().getName();
        String msg = "Hello am " + name + " i wnat to talk to you about " + realestate.getTitle();
        SocialUtils.whatsappMsg(v.getContext(), "966559563649", msg);
        toggleVisible();
    }

    public void orderCall(View v) {
        SocialUtils.dial(v.getContext(), realestate.getUserBrief().getPhone());
        toggleVisible();
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(realestate.getLang(), -realestate.getLat())));
        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")

                // Add the SymbolLayer icon image to the map style
                //    .withImage(ICON_ID, R.drawable.ic_location)

                // Adding a GeoJson source for the SymbolLayer icons.
                .withSource(new GeoJsonSource(SOURCE_ID,
                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))

                // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
                // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
                // the coordinate point. This is offset is not always needed and is dependent on the image
                // that you use for the SymbolLayer icon.
                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(PropertyFactory.iconImage(ICON_ID),
                                iconAllowOverlap(true),
                                iconOffset(new Float[]{0f, -9f}))
                ), style -> {
            // Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.

        });
    }
}
