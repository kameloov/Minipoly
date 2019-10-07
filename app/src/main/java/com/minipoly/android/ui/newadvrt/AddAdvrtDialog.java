package com.minipoly.android.ui.newadvrt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.mapbox.api.geocoding.v5.models.CarmenContext;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker;
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions;
import com.minipoly.android.R;
import com.minipoly.android.databinding.AddAdvrtDialogBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.repository.MiscRepository;
import com.minipoly.android.ui.category_dialog.CategoryDialog;
import com.minipoly.android.ui.gallery.GalleryDialog;
import com.minipoly.android.utils.MapUtils;

import static android.app.Activity.RESULT_OK;

public class AddAdvrtDialog extends DialogFragment {

    private AddAdvrtDialogViewModel model;
    private AddAdvrtDialogBinding binding;
    private static final int PLACE_SELECTION_REQUEST_CODE = 56789;

    public static AddAdvrtDialog newInstance() {
        return new AddAdvrtDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_Dialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        binding = AddAdvrtDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(AddAdvrtDialogViewModel.class);
        binding.setLifecycleOwner(this);
        binding.imgLocation.setOnClickListener(v -> pickLocation());
        binding.setVm(model);
        attachEvents();
        Realestate realestate = AddAdvrtDialogArgs.fromBundle(getArguments()).getItem();
        model.setRealestate(realestate);
        addObservers();
    }

    public void pickLocation() {
        Intent intent = new PlacePicker.IntentBuilder()
                .accessToken(Mapbox.getAccessToken())
                .placeOptions(
                        PlacePickerOptions.builder()
                                .statingCameraPosition(
                                        new CameraPosition.Builder()
                                                .target(new LatLng(30, 22))
                                                .zoom(2)
                                                .build())
                                .includeReverseGeocode(true)
                                .build())
                .build(getActivity());
        startActivityForResult(intent, PLACE_SELECTION_REQUEST_CODE);
    }

    private void addObservers() {
        model.kindRadio.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                model.category.setValue(null);
                model.subCategory.setValue(null);
                model.extraVisible.setValue(false);
            }
        });

        model.command.observe(this, command -> {
            if (command == AddAdvrtDialogViewModel.Command.IDLE)
                return;
            String id = command == AddAdvrtDialogViewModel.Command.SHOW_CATEGORY ?
                    null : model.category.getValue().getId();
            CategoryDialog dialog = CategoryDialog.newInstance(id, !model.kindRadio.isChecked(), (sub, category) -> model.setCatOrSubId(sub, category));

            dialog.show(getFragmentManager(), "CAT");
            model.command.setValue(AddAdvrtDialogViewModel.Command.IDLE);
        });

        model.images.observe(this, images -> {
            if (images != null && images.size() > 0)
                Glide.with(this).load(images.get(0).getUri()).into(binding.imgGallery);
        });
        model.getSuccess().observe(this, a -> {
            Log.e("add", "addRealestate: " + a);
            Toast.makeText(getContext(),
                    getString(a ? R.string.added : R.string.error_add), Toast.LENGTH_SHORT).show();
            if (a)
                dismiss();
        });

    }

    private void attachEvents() {
        binding.imgGallery.setOnClickListener((v) -> showGallery());
        binding.txtAdd.setOnClickListener(v -> {
            if (model.isDataMissing())
                Toast.makeText(getContext(), getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
            else {
                model.addRealestate();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PLACE_SELECTION_REQUEST_CODE && resultCode == RESULT_OK) {
            CarmenFeature carmenFeature = PlacePicker.getPlace(data);
            if (carmenFeature == null || carmenFeature.context() == null)
                return;
            String country = "";
            String id = "";
            if (carmenFeature.id().contains("region"))
                id = carmenFeature.id();
            for (CarmenContext context : carmenFeature.context()) {
                if (context.id().contains("country"))
                    country = context.shortCode().toUpperCase();
                if (context.id().contains("region"))
                    id = context.id();
            }
            String finalCountry = country;
            String finalId = id;
            float lat = (float) carmenFeature.center().latitude();
            float lang = (float) carmenFeature.center().longitude();
            MiscRepository.getcity(id, country, (success, dbCity) -> {
                if (success && dbCity != null) {
                    model.setLocationData(dbCity, lat, lang);
                } else {
                    MapUtils.cityById(finalId, finalCountry, (success1, mapCity) -> {
                        if (success1 && mapCity != null) {
                            MiscRepository.addCity(mapCity);
                            model.setLocationData(mapCity, lat, lang);
                        } else
                            Toast.makeText(getContext(), getString(R.string.location_error), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
    }

    private void showGallery() {
        GalleryDialog dialog = GalleryDialog.newInstance();
        dialog.show(getFragmentManager(), "gallery");
    }


}
