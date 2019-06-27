package com.minipoly.android.ui.main;

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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.R;
import com.minipoly.android.adapter.CategoryAdapter;
import com.minipoly.android.databinding.MainFragmentBinding;
import com.minipoly.android.utils.PermissionUtils;
import com.minipoly.android.utils.UriUtils;


public class MainFragment extends Fragment {
    private MainViewModel mViewModel;
    private   MainFragmentBinding binding;
    private int SELECT_IMAGE = 1000;
    private boolean afterKITKAT = Build.VERSION.SDK_INT > 19;
    private CategoryAdapter adapter = new CategoryAdapter();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      binding= DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setViewmodel(mViewModel);
        binding.setLifecycleOwner(this);
        binding.button2.setOnClickListener((v)->upload());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        binding.lstCategory.setLayoutManager(manager);
        binding.lstCategory.setAdapter(adapter);
        mViewModel.getCategories().observe(this,adapter::submitList);

    }

    private void upload(){
        if (PermissionUtils.hasOrrequestPermissions(this))
            startGallery();
    }

    private void startGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK){
            if (requestCode==SELECT_IMAGE){
                Uri selectedImageUri = null;
                if (afterKITKAT) {
                    selectedImageUri = Uri.parse("file://" + UriUtils.getPath(getActivity(), data.getData()));
                } else {
                    selectedImageUri = data.getData();
                }
                mViewModel.upload("test.png",selectedImageUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PermissionUtils.STRAGE_PERMISSIONS_CODE)
            if (PermissionUtils.permissionsGranted(grantResults))
                startGallery();
    }
}
