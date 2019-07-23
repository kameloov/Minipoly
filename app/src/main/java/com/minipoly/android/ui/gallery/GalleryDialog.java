package com.minipoly.android.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.ProgressInfo;
import com.minipoly.android.R;
import com.minipoly.android.adapter.ImageAdapter;
import com.minipoly.android.databinding.GalleryDialogBinding;
import com.minipoly.android.utils.ImageBuffer;
import com.minipoly.android.utils.PermissionUtils;
import com.minipoly.android.utils.UriUtils;

public class GalleryDialog extends DialogFragment {

    private GalleryDialogViewModel model;
    private int SELECT_IMAGE = 1000;
    private boolean afterKITKAT = Build.VERSION.SDK_INT > 19;
    private GalleryDialogBinding binding;
    private ImageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Dialog);
    }


    public static GalleryDialog newInstance() {
        GalleryDialog dialog = new GalleryDialog();
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GalleryDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(GalleryDialogViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setModel(model);
        binding.btnOk.setOnClickListener(v -> {
            if (model.getInfo().getValue() != null && model.getInfo().getValue().getStatus() == ProgressInfo.SUCCESS)
                dismiss();
            else
                model.Upload();
        });
        prepareRecycler();
        attachObservers();
    }


    private void attachObservers() {
        model.getImages().observe(this, images -> adapter.submitList(images));
    }

    private void prepareRecycler() {
        adapter = new ImageAdapter(v -> startGallery());
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 3);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
    }


    private void startGallery() {
        if (PermissionUtils.hasOrrequestPermissions(this)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                Uri selectedImageUri = null;
                if (afterKITKAT) {
                    selectedImageUri = Uri.parse("file://" + UriUtils.getPath(getActivity(), data.getData()));
                } else {
                    selectedImageUri = data.getData();
                }
                ImageBuffer.addImage(selectedImageUri);
            }
        }
    }

}
