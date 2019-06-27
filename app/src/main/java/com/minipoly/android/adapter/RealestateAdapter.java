package com.minipoly.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;

import com.minipoly.android.databinding.ListItemAdvrtBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.ui.home.HomeFragmentDirections;

import java.util.List;

public class RealestateAdapter extends PagerAdapter {

    private List<Realestate> realestates;
    private Context context;

    public RealestateAdapter(List<Realestate> realestates, Context context) {
        this.realestates = realestates;
        this.context = context;
    }

    public List<Realestate> getRealestates() {
        return realestates;
    }

    public void setRealestates(List<Realestate> realestates) {
        this.realestates = realestates;
        notifyDataSetChanged();
    }


    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ListItemAdvrtBinding binding = ListItemAdvrtBinding.inflate(inflater, container, false);
        binding.setAd(realestates.get(position));
        // todo make the language dynamic
        binding.setArabic(false);
        View v = binding.getRoot();
        v.setOnClickListener(v1 -> {
            HomeFragmentDirections.RealestateDetails realestateDetails =
                    HomeFragmentDirections.realestateDetails(realestates.get(position));
            Navigation.findNavController(v).navigate(realestateDetails);
        });
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return realestates.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
