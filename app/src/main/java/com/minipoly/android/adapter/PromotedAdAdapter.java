package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.minipoly.android.databinding.ListItemPromotedAdBinding;
import com.minipoly.android.entity.PromotedAd;

import java.util.Date;
import java.util.List;

public class PromotedAdAdapter extends PagerAdapter {

    private List<PromotedAd> ads;

    public PromotedAdAdapter(List<PromotedAd> ads) {
        this.ads = ads;
    }

    public List<PromotedAd> getAds() {
        return ads;
    }

    public void setAds(List<PromotedAd> ads) {
        this.ads = ads;
        notifyDataSetChanged();
    }


    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        ListItemPromotedAdBinding binding = ListItemPromotedAdBinding.inflate(inflater, container, false);
        binding.setAd(ads.get(position));
        // todo make the language dynamic
        binding.setArabic(false);
        binding.setNow(new Date());
        View v = binding.getRoot();
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return ads.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
