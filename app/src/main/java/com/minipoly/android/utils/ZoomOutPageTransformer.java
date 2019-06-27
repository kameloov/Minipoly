package com.minipoly.android.utils;

import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.45f;
    private static final float MAX_SCALE = 0.6f;
    private static final float MIN_FADE = 0.8f;
    private static final float FACTOR = 1.4f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -2) {
            view.setAlpha(MIN_FADE);
        } else if (position < 0) {
            view.setAlpha(1 + position * (1 - MIN_FADE));
            view.setTranslationX(-pageWidth * FACTOR * MAX_SCALE * position);
            ViewCompat.setTranslationZ(view, position);
            float scaleFactor = MIN_SCALE
                    + (MAX_SCALE - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else if (position == 0) {
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(MAX_SCALE);
            ViewCompat.setTranslationZ(view, 0);
            view.setScaleY(MAX_SCALE);
        } else if (position <= 2) {
            ViewCompat.setTranslationZ(view, -position);
            view.setAlpha(1 - position * (1 - MIN_FADE));
            view.setTranslationX(pageWidth * FACTOR * MAX_SCALE * -position);

            float scaleFactor = MIN_SCALE
                    + (MAX_SCALE - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else {
            view.setAlpha(MIN_FADE);
        }
    }

}
