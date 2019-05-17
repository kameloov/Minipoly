package com.minipoly.android.ui.newadvrt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.minipoly.android.R;
import com.minipoly.android.databinding.AddAdvrtDialogBinding;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.ui.gallery.GalleryDialog;

public class AddAdvrtDialog extends DialogFragment {

    private AddAdvrtDialogViewModel model;
    private AddAdvrtDialogBinding binding;

    public static AddAdvrtDialog newInstance() {
        return new AddAdvrtDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Dialog);
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
        binding.setVm(model);
        binding.imgGallery.setOnClickListener((v) -> showGallery());
        Realestate realestate = AddAdvrtDialogArgs.fromBundle(getArguments()).getItem();
        CustomRadio cr = new CustomRadio();
        cr.setName1("For Rent");
        cr.setName2("For Sale");
        cr.setChecked(false);
        model.setRadio(cr);
        model.setRealestate(realestate);
    }

    private void showGallery() {
        GalleryDialog dialog = GalleryDialog.newInstance();
        dialog.show(getFragmentManager(), "gallery");
    }


}
