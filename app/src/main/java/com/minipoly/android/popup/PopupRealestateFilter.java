package com.minipoly.android.popup;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.databinding.RealestateFilterBinding;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.RealestateFilterBuilder;
import com.minipoly.android.entity.ValueFilter;
import com.minipoly.android.utils.NumberInput;

import java.util.List;

public class PopupRealestateFilter {
    public NumberInput rooms = new NumberInput();
    public NumberInput bathrooms = new NumberInput();
    public CustomRadio priceOrder = new CustomRadio(false, "Low first", "High first");
    public CustomRadio areaOrder = new CustomRadio(false, "Low first", "High first");
    public MutableLiveData<Boolean> monthRent = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> yearRent = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> furnished = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> unfurnished = new MutableLiveData<>(false);
    private PopupWindow window;
    private RealestateFilterBinding binding;
    private View anchor;
    private IRealestateFilter iRealestateFilter;

    public PopupRealestateFilter(IRealestateFilter iRealestateFilter) {
        this.iRealestateFilter = iRealestateFilter;
    }

    public void setAnchor(View anchor) {
        this.anchor = anchor;
        binding = RealestateFilterBinding.inflate(LayoutInflater.from(this.anchor.getContext()));
        binding.setPop(this);
        window = new PopupWindow(binding.getRoot(), LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void cancel() {
        if (window.isShowing())
            window.dismiss();
    }

    public boolean isShowing() {
        if (window == null)
            return false;
        return window.isShowing();
    }

    public void ok() {
        if (window.isShowing()) {
            RealestateFilterBuilder builder = new RealestateFilterBuilder();
            builder.setOrderType(RealestateFilterBuilder.OrderType.DATE_ASC)
                    .setRoomCount(rooms.getValue()).setBathroomCount(bathrooms.getValue())
                    .setMonthly(monthRent.getValue()).setYearly(yearRent.getValue())
                    .setFurnished(furnished.getValue()).build();
            iRealestateFilter.onFilter(builder.getFilters());
            window.dismiss();
        }
    }

    public void show() {
        window.showAsDropDown(anchor, -(window.getWidth() + anchor.getLayoutParams().width), 0);
        window.setFocusable(true);
        window.update();
    }

    public interface IRealestateFilter {
        void onFilter(List<ValueFilter> filters);
    }

}
