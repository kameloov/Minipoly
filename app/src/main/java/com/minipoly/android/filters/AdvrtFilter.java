package com.minipoly.android.filters;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.minipoly.android.entity.ValueFilter;

import java.util.ArrayList;
import java.util.List;

public class AdvrtFilter extends BaseObservable {
    private int minPrice;
    private int maxPrice;
    private boolean market;
    private int zoom;
    private String square;
    private String categoryId;
    private String subCategoryId;

    @Bindable
    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
        notifyPropertyChanged(BR.subCategoryId);
    }

    @Bindable
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }

    public int getMinPrice() {
        return minPrice;
    }

    public AdvrtFilter setMinPrice(int minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public AdvrtFilter setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public boolean isMarket() {
        return market;
    }

    public AdvrtFilter setMarket(boolean market) {
        this.market = market;
        return this;
    }

    public int getZoom() {
        return zoom;
    }

    public AdvrtFilter setZoom(int zoom) {
        this.zoom = zoom;
        return this;
    }

    public String getSquare() {
        return square;
    }

    public AdvrtFilter setSquare(String square) {
        this.square = square;
        return this;
    }

    public List<ValueFilter> getFilters() {
        ArrayList<ValueFilter> filters = new ArrayList<>();
        if (categoryId != null)
            filters.add(new ValueFilter("categoryId", ValueFilter.FilterType.EQUAL, categoryId));
        if (subCategoryId != null)
            filters.add(new ValueFilter("subCategoryId", ValueFilter.FilterType.EQUAL, subCategoryId));

        filters.add(new ValueFilter("market", ValueFilter.FilterType.EQUAL, market));
        filters.add(new ValueFilter("l" + zoom, ValueFilter.FilterType.EQUAL, square));
        filters.add(new ValueFilter("price", ValueFilter.FilterType.RANGE, minPrice, maxPrice));
        return filters;
    }
}
