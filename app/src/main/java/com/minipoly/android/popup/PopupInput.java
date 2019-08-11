package com.minipoly.android.popup;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.databinding.PopupInputBinding;

public class PopupInput {
    public MutableLiveData<String> text = new MutableLiveData<>("");
    private PopupWindow window;
    private PopupInputBinding binding;
    private TexInputCompleteListener listener;
    private View parent;

    public PopupInput(View parent, TexInputCompleteListener listener) {
        this.parent = parent;
        this.listener = listener;
        binding = PopupInputBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.setPop(this);
        window = new PopupWindow(binding.getRoot(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void cancel() {
        if (listener != null)
            listener.onTextInputCompleted(false, "");
        if (window.isShowing())
            window.dismiss();
    }

    public void ok() {
        if (listener != null)
            listener.onTextInputCompleted(true, text.getValue());
        if (window.isShowing())
            window.dismiss();
    }

    public void show() {
        window.showAtLocation(parent, Gravity.CENTER, 0, 0);
        window.setFocusable(true);
        window.update();
    }

    public interface TexInputCompleteListener {
        void onTextInputCompleted(boolean positive, String text);
    }
}
