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

import com.minipoly.android.R;
import com.minipoly.android.databinding.GalleryDialogBinding;
import com.minipoly.android.utils.PermissionUtils;
import com.minipoly.android.utils.UriUtils;

public class GalleryDialog extends DialogFragment {

    private GalleryDialogViewModel model;
    private int SELECT_IMAGE = 1000;
    private boolean afterKITKAT = Build.VERSION.SDK_INT > 19;
    private GalleryDialogBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Dialog);
    }

    public static GalleryDialog newInstance() {
        return new GalleryDialog();
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
        binding.textView26.setOnClickListener(v -> upload());
        prepareRecycler();
        upload();
    }

    private void prepareRecycler() {
        if (model.getAdapter() == null) ;
        model.initAdapter(v -> upload());
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 3);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(model.getAdapter());
    }

    private void upload() {
        if (PermissionUtils.hasOrrequestPermissions(this))
            startGallery();
    }

    private void startGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
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
                model.addImage(selectedImageUri);
            }
        }
    }

}
