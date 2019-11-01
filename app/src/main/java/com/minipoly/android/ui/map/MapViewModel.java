package com.minipoly.android.ui.map;

import android.location.Geocoder;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.minipoly.android.R;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.City;
import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.ValueFilter;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.AuctionRepository;
import com.minipoly.android.repository.CategoryRepository;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.utils.CardBuilder;

import java.util.List;


public class MapViewModel extends ViewModel {
    private MutableLiveData<List<Country>> countries = new MutableLiveData<>();
    private MutableLiveData<List<City>> cities = new MutableLiveData<>();
    private MutableLiveData<List<Realestate>> realestates = new MutableLiveData<>();
    private GoogleMap googleMap;
    private Geocoder geocoder;
    private CardBuilder cardBuilder;
    private MutableLiveData<Boolean> addVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> filtersVisible = new MutableLiveData<>();
    public FireLiveQuery<Category> categories;
    public MutableLiveData<Integer> minPrice = new MutableLiveData<>();
    public MutableLiveData<Integer> maxPrice = new MutableLiveData<>();
    public MutableLiveData<String> catgory = new MutableLiveData<>();
    public MutableLiveData<CustomRadio> rent = new MutableLiveData<>();
    public MutableLiveData<CustomRadio> realestate = new MutableLiveData<>();

    public MapViewModel() {
        categories = CategoryRepository.getCategories(false);
        filtersVisible.setValue(true);
        rent.setValue(new CustomRadio(false, "For Rent", "For Sale"));
        realestate.setValue(new CustomRadio(false, "Realestate", "Market"));

    }


    public LiveData<Boolean> getFiltersVisible() {
        return filtersVisible;
    }

    public void toggleFilters() {
        filtersVisible.setValue(!filtersVisible.getValue());
    }

    public LiveData<Boolean> getAddVisible() {
        return addVisible;
    }

    public void showMenu() {
        addVisible.setValue(true);
    }

    public void hideMenu() {
        addVisible.setValue(false);
    }

    public void setCardBuilder(CardBuilder cardBuilder) {
        this.cardBuilder = cardBuilder;
    }

    public LiveData<List<Country>> getCountries() {
        return countries;
    }

    public LiveData<List<City>> getCities() {
        return cities;
    }

    public LiveData<List<Realestate>> getRealestates() {
        return realestates;
    }

    public void update() {
 /*       VisibleRegion region = googleMap.getProjection().getVisibleRegion();
        float zoom = googleMap.getCameraPosition().zoom;
        if (zoom > 8)
            HttpService.getRealestates(realestates, region.latLngBounds.southwest, region.latLngBounds.northeast,
                    minPrice.getValue(), maxPrice.getValue(), rent.getValue().isChecked(), catgory.getValue());
        else
            MiscRepository.getCountries(getLongFilter(region), countries);*/
    }

    public void setMapAndGeocoder(GoogleMap map, Geocoder geocoder) {
        this.googleMap = map;
        this.geocoder = geocoder;
    }

    public void addRealestate(View view,LatLng latLng) {
        addVisible.setValue(false);
        MapFragmentDirections.AddRealestate addRealestate = MapFragmentDirections
                .addRealestate(RealestateRepository.generateRealestate(latLng.latitude,latLng.longitude,geocoder));
        Navigation.findNavController(view).navigate(addRealestate);
    }

    public void addAuction(View view, LatLng latLng) {
        MapFragmentDirections.ActionMapFragmentToAddAuctionDialog action = MapFragmentDirections
                .actionMapFragmentToAddAuctionDialog(AuctionRepository.generateAuction(latLng.latitude, latLng.longitude, geocoder));
        Navigation.findNavController(view).navigate(action);
    }

    public void drawCountries(List<Country> countries) {
        if (googleMap == null)
            return;
        googleMap.clear();
        for (Country c : countries) {
            LatLng pos = new LatLng(c.getLat(), c.getLang());
            float zoom = googleMap.getCameraPosition().zoom;
            googleMap.addMarker(new MarkerOptions().position(pos)
                    .title(c.getName()).anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromBitmap(cardBuilder.generateCard(c, zoom))));
        }
    }

    public void drawRealestates(List<Realestate> realestates) {
        if (googleMap == null)
            return;
        googleMap.clear();
        for (Realestate r : realestates) {
            LatLng pos = new LatLng(r.getLat(), r.getLang());
            float zoom = googleMap.getCameraPosition().zoom;
            Marker m = googleMap.addMarker(new MarkerOptions().position(pos)
                    .title(r.getTitle()).anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.house)));
        }
    }

    private ValueFilter<Double> getLongFilter(VisibleRegion region) {
        double min = region.latLngBounds.southwest.longitude;
        double max = region.latLngBounds.northeast.longitude;
        return new ValueFilter<>("lang", ValueFilter.FilterType.RANGE, min, max);
    }

    private ValueFilter<Double> getLatFilter(VisibleRegion region) {
        double min = region.latLngBounds.southwest.latitude;
        double max = region.latLngBounds.northeast.latitude;
        return new ValueFilter<>("lat", ValueFilter.FilterType.RANGE, min, max);
    }


}
