package com.minipoly.android.ui.card_dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.minipoly.android.databinding.RealestateCardDialogBinding;

public class RealestateCardDialog extends DialogFragment {

    private RealestateCardViewModel model;
    private RealestateCardDialogBinding binding;

    public static RealestateCardDialog newInstance() {
        return new RealestateCardDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RealestateCardDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(RealestateCardViewModel.class);
        model.setRealestate(RealestateCardDialogArgs.fromBundle(getArguments()).getRealestate());
        binding.setLifecycleOwner(this);
        binding.setVm(model);
    }

}
