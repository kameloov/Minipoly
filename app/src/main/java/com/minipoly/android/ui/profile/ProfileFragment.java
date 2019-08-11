package com.minipoly.android.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.minipoly.android.R;
import com.minipoly.android.databinding.ProfileFragmentBinding;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel model;
    private ProfileFragmentBinding binding;
    private String userId;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userId = ProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        model = ViewModelProviders.of(this, new ProfileViewModelFactory(userId)).get(ProfileViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        attachObservers();
    }

    private void attachObservers() {
        model.about.observe(this, aBoolean -> {
            if (aBoolean)
                showAbout();
            else
                showDeals();
        });
        model.chartData.observe(this, pieEntries -> {
            if (pieEntries != null && pieEntries.size() > 0)
                prepareChart(pieEntries);
        });
    }

    private void prepareChart(List<PieEntry> pieEntries) {
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(new int[]{R.color.chart1, R.color.chart2, R.color.chart3}, getContext());
        PieChart chart = binding.pieChart;
        chart.setDrawCenterText(false);
        chart.setDrawHoleEnabled(false);
        chart.setDrawEntryLabels(false);
        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.invalidate();
    }

    private void showAbout() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_container, UserAboutFragment.newInstance(userId));
        transaction.commit();
    }

    private void showDeals() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_container, UserDealsFragment.newInstance(userId, model.ads.getValue()));
        transaction.commit();
    }
}
