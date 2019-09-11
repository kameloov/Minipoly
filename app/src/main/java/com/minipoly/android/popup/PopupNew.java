package com.minipoly.android.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.minipoly.android.databinding.PopupNewBinding;

public class PopupNew extends PopupWindow {

    private INewItemListener listener;
    private PopupNewBinding binding;

    public PopupNew(Context context, INewItemListener listener) {
        super(context);
        binding = PopupNewBinding.inflate(LayoutInflater.from(context));
        binding.setPop(this);
        setContentView(binding.getRoot());
        setFocusable(true);
        setOutsideTouchable(true);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        this.listener = listener;
    }

    public void select(NewType type) {
        if (listener != null)
            listener.onNewSelected(type);
        Log.e("select: ", "selected " + type);
        dismiss();
    }


    public void show(View parent) {
        getContentView().measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        showAtLocation(parent, Gravity.NO_GRAVITY, 0, (int) (parent.getY() - getContentView().getMeasuredHeight() - 20));
        float y = parent.getY();
        int height = getContentView().getMeasuredHeight();
        Log.e("show: ", "showing popup");
    }

    public enum NewType {NT_DEAL, NT_AUCTION, NT_AD}

    public interface INewItemListener {
        void onNewSelected(NewType type);
    }
}
