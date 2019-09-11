package com.minipoly.android.ui.add_ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.minipoly.android.R;
import com.minipoly.android.databinding.AddAdFragmentBinding;
import com.minipoly.android.ui.gallery.GalleryDialog;
import com.minipoly.android.ui.location_dialog.LocationDialog;

public class AddAdFragment extends DialogFragment {

    private AddAdViewModel model;
    private AddAdFragmentBinding binding;

    public static AddAdFragment newInstance() {
        return new AddAdFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        binding = AddAdFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(AddAdViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        attachEvents();
        attachObservers();
    }


    private void attachEvents() {
        binding.imgGallery.setOnClickListener((v) -> showGallery());
        binding.txtAdd.setOnClickListener(v -> {
            if (model.isDataMissing())
                Toast.makeText(getContext(), getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
            else {
                model.addAd();
            }
        });
    }

    private void attachObservers() {

        model.images.observe(this, images -> {
            if (images != null && images.size() > 0)
                Glide.with(this).load(images.get(0).getUri()).into(binding.imgGallery);
        });

        model.command.observe(this, command -> {
            if (command == AddAdViewModel.Command.IDLE)
                return;
            switch (command) {
                case SUCCESS:
                    Toast.makeText(getContext(), getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    model.command.setValue(AddAdViewModel.Command.IDLE);
                    break;
                case FAILED:
                    Toast.makeText(getContext(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                    model.command.setValue(AddAdViewModel.Command.IDLE);
                    break;
                case SHOW_LOCATION:
                    LocationDialog dialog = LocationDialog.newInstance(null, (city, country) -> {
                        if (country != null)
                            model.setCountry(country);
                    });
                    dialog.show(getFragmentManager(), "location");
                    model.command.setValue(AddAdViewModel.Command.IDLE);
                    break;
                case DATA_MISSING:
                    Toast.makeText(getContext(), getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
                    model.command.setValue(AddAdViewModel.Command.IDLE);
            }
        });
    }


    private void showGallery() {
        GalleryDialog dialog = GalleryDialog.newInstance();
        dialog.show(getFragmentManager(), "gallery");
    }
}
