package com.minipoly.android.popup;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.databinding.PopupDiscountBinding;

public class PopupDiscount {
    public MutableLiveData<String> text = new MutableLiveData<>("");
    public MutableLiveData<Integer> days = new MutableLiveData<>(0);
    private PopupWindow window;
    private PopupDiscountBinding binding;
    private DiscountInputListener listener;
    private View parent;
    private MutableLiveData<Integer> discount = new MutableLiveData<>(0);


    public PopupDiscount(View parent, DiscountInputListener listener) {
        this.parent = parent;
        this.listener = listener;
        binding = PopupDiscountBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.setPop(this);
        window = new PopupWindow(binding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public String getDiscount() {
        return String.valueOf(discount.getValue());
    }

    public void setDiscount(String value) {
        int dis = value.length() == 0 ? 0 : Integer.parseInt(value);
        if (dis > 100)
            dis = 100;
        if (dis < 0)
            dis = 0;
        discount.setValue(dis);
    }

    public void changeDays(int i) {
        int d = days.getValue();
        days.postValue(d + i);
        binding.txtDuration.setText(days.getValue() + "");
        Log.e("changeDays: ", "new value is " + days.getValue());

    }

    public void cancel() {
        if (window.isShowing())
            window.dismiss();
    }

    public void ok() {
        if (listener != null)
            listener.onDiscount(days.getValue(), discount.getValue());
        if (window.isShowing())
            window.dismiss();
    }

    public void show() {
        window.showAtLocation(parent, Gravity.CENTER, 0, 0);
        window.setFocusable(true);
        window.update();
    }

    public interface DiscountInputListener {
        void onDiscount(int days, float percent);
    }
}
