package com.minipoly.android;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyPager extends ViewPager {
    public MyPager(@NonNull Context context) {
        super(context);
    }

    public MyPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (i == childCount - 1) {
            return getCurrentItem();
        } else {
            return i == getCurrentItem() ? i + 1 : i;
        }
    }
}
