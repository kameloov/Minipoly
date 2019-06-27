package com.minipoly.android.ui.edit_profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.request.RequestOptions;
import com.minipoly.android.ProgressInfo;
import com.minipoly.android.R;
import com.minipoly.android.databinding.EditProfileFragmentBinding;
import com.minipoly.android.utils.GlideApp;
import com.minipoly.android.utils.LocalData;
import com.minipoly.android.utils.PermissionUtils;
import com.minipoly.android.utils.UriUtils;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel model;
    private int SELECT_IMAGE = 1100;
    private boolean afterKITKAT = Build.VERSION.SDK_INT > 19;
    private EditProfileFragmentBinding binding;
    private Uri selectedImageUri;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = EditProfileFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(EditProfileViewModel.class);
        model.setUser(LocalData.getUserInfo());
        binding.setLifecycleOwner(this);
        binding.setM(model);
        attachEvents();
        attachObservers();
    }

    private void attachEvents() {
        binding.imgProfile.setOnClickListener((v -> upload()));
        binding.btnChange.setOnClickListener(v -> {
        });
    }

    private void attachObservers() {
        model.getUploader().observe(this, transferInfo -> {
            if (transferInfo.getStatus() == ProgressInfo.SUCCESS)
                GlideApp.with(this).load(selectedImageUri)
                        .apply(new RequestOptions().circleCrop().error(R.drawable.circle))
                        .into(binding.imgProfile);
        });
        model.getSuccess().observe(this, a -> {
            Toast.makeText(getContext(), getString(a ? R.string.update_success : R.string.error), Toast.LENGTH_SHORT).show();
        });
    }

    private void upload() {
        if (PermissionUtils.hasOrrequestPermissions(this))
            startGallery();
    }

    private void startGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                if (afterKITKAT) {
                    selectedImageUri = Uri.parse("file://" + UriUtils.getPath(getActivity(), data.getData()));
                } else {
                    selectedImageUri = data.getData();
                }
                model.upload(selectedImageUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.STRAGE_PERMISSIONS_CODE)
            if (PermissionUtils.permissionsGranted(grantResults))
                startGallery();
    }
}


