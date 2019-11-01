package com.minipoly.android.ui.filters.realestate;

import android.view.LayoutInflater;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.minipoly.android.entity.Category;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.filters.RealestateFilter;
import com.minipoly.android.livedata.FireLiveQuery;
import com.minipoly.android.repository.CategoryRepository;
import com.minipoly.android.utils.MapUtils;

import timber.log.Timber;

public class RealestateFilterViewModel extends ViewModel {

    public FireLiveQuery<Category> categories = CategoryRepository.getCategories(false);
    public MutableLiveData<CustomRadio> rent = new MutableLiveData<>();
    public RealestateFilter filter;

    public RealestateFilterViewModel(RealestateFilter filter) {
        this.filter = filter;
        rent.setValue(new CustomRadio(false, "For Rent", "For Sale"));
    }

    public void updateRegion(LatLngBounds bounds, LayoutInflater inflater) {
        int zoom = MapUtils.getZoomLevel(bounds);
        String coord = MapUtils.getCoordinatesString(zoom, bounds.getCenter());
        Timber.e("zoom : " + zoom + "and coord is " + coord);
        filter.setZoom(zoom);
        filter.setSquare(coord);
    }

    public void changeRooms(int delta) {
        filter.setRooms(filter.getRooms() + delta);
    }

    public void changeBathrooms(int delta) {
        filter.setBathrooms(filter.getBathrooms() + delta);
    }

    public void setMonthly(boolean value) {
        filter.setMonthly(value);
    }

    public void setfurnished(boolean val) {
        filter.setFurnished(val);
    }

    public void setCategory(Category c) {
        if (c != null)
            filter.setCategoryId(c.getId());
    }

    public void setSubCategory(Category c) {
        if (c != null)
            filter.setSubCategoryId(c.getId());
    }
}
