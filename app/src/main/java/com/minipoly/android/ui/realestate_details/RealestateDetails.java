package com.minipoly.android.ui.realestate_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.minipoly.android.databinding.RealestateDetailsFragmentBinding;
import com.minipoly.android.entity.Realestate;

public class RealestateDetails extends Fragment {

    private RealestateDetailsViewModel model;
    private RealestateDetailsFragmentBinding binding;

    public static RealestateDetails newInstance() {
        return new RealestateDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RealestateDetailsFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(RealestateDetailsViewModel.class);
        Realestate realestate = RealestateDetailsArgs.fromBundle(getArguments()).getRealestate();
        model.setRelestate(realestate);
        binding.setLifecycleOwner(this);
        binding.setM(model);
    }

}
