package com.minipoly.android.ui.add_auction;

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
import com.minipoly.android.databinding.AddAuctionDialogBinding;
import com.minipoly.android.entity.Auction;
import com.minipoly.android.ui.category_dialog.CategoryDialog;
import com.minipoly.android.ui.gallery.GalleryDialog;

public class AddAuctionDialog extends DialogFragment {

    private AddAuctionViewModel model;
    private AddAuctionDialogBinding binding;

    public static AddAuctionDialog newInstance() {
        return new AddAuctionDialog();
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
        binding = AddAuctionDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(AddAuctionViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        attachEvents();
        Auction realestate = AddAuctionDialogArgs.fromBundle(getArguments()).getAuction();
        model.setAuction(realestate);
        addObservers();
    }

    private void addObservers() {
        model.kindRadio.observe(this, customRadio -> model.extraVisible.setValue(false));

        model.command.observe(this, command -> {
            if (command == AddAuctionViewModel.Command.IDLE)
                return;
            String id = command == AddAuctionViewModel.Command.SHOW_CATEGORY ?
                    null : model.category.getValue().getId();
            CategoryDialog dialog = CategoryDialog.newInstance(id, !model.kindRadio.getValue().isChecked(), (sub, category) -> model.setCatOrSubId(sub, category));

            dialog.show(getFragmentManager(), "CAT");
            model.command.setValue(AddAuctionViewModel.Command.IDLE);
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
