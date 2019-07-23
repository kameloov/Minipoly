package com.minipoly.android.ui.newadvrt;

import android.os.Bundle;
import android.util.Log;
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
import com.minipoly.android.databinding.AddAdvrtDialogBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.ui.category_dialog.CategoryDialog;
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
        binding.setVm(model);
        attachEvents();
        Realestate realestate = AddAdvrtDialogArgs.fromBundle(getArguments()).getItem();
        model.setRealestate(realestate);
        addObservers();
    }

    private void addObservers() {
        model.kindRadio.observe(this, customRadio -> model.extraVisible.setValue(false));

        model.command.observe(this, command -> {
            if (command == AddAdvrtDialogViewModel.Command.IDLE)
                return;
            String id = command == AddAdvrtDialogViewModel.Command.SHOW_CATEGORY ?
                    null : model.category.getValue().getId();
            CategoryDialog dialog = CategoryDialog.newInstance(id, !model.kindRadio.getValue().isChecked(), (sub, category) -> model.setCatOrSubId(sub, category));

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


    private void showGallery() {
        GalleryDialog dialog = GalleryDialog.newInstance();
        dialog.show(getFragmentManager(), "gallery");
    }


}
