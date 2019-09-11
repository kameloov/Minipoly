package com.minipoly.android.popup;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.C;
import com.minipoly.android.databinding.PopupPromoteBinding;

public class PopupPromote {
    public MutableLiveData<String> text = new MutableLiveData<>("");
    public MutableLiveData<Integer> days = new MutableLiveData<>(0);
    private PopupWindow window;
    private PopupPromoteBinding binding;
    private PromoteListener listener;
    private View parent;


    public PopupPromote(View parent, PromoteListener listener) {
        this.parent = parent;
        this.listener = listener;
        binding = PopupPromoteBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.setPop(this);
        window = new PopupWindow(binding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void changeDays(int i) {
        int d = days.getValue();
        if (d + i < 0)
            return;
        days.postValue(d + i);
        binding.txtDuration.setText("" + days.getValue());
        binding.txtCost.setText("" + days.getValue() * C.PROMOTE_PRICE);
    }

    public void cancel() {
        if (window.isShowing())
            window.dismiss();
    }

    public void ok() {
        if (listener != null)
            listener.onPromote(days.getValue());
        if (window.isShowing())
            window.dismiss();
    }

    public void show() {
        window.showAtLocation(parent, Gravity.CENTER, 0, 0);
        window.setFocusable(true);
        window.update();
    }

    public interface PromoteListener {
        void onPromote(int days);
    }
}
