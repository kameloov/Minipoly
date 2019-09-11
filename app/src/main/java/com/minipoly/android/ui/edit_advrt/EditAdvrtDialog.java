package com.minipoly.android.ui.edit_advrt;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.minipoly.android.databinding.EditAdvrtFragmentBinding;
import com.minipoly.android.entity.Realestate;

public class EditAdvrtDialog extends DialogFragment {

    private EditAdvrtViewModel model;
    private EditAdvrtFragmentBinding binding;

    public static EditAdvrtDialog newInstance() {
        return new EditAdvrtDialog();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        binding = EditAdvrtFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Realestate r = EditAdvrtDialogArgs.fromBundle(getArguments()).getRealestate();
        model = ViewModelProviders.of(this, new EditAdvrtModelFactory(r)).get(EditAdvrtViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        // attachObservers();
    }

    private void attachObservers() {
        model.message.observe(this, s -> {
            if (s == null || s.length() == 0)
                return;
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            model.message.setValue("");
        });
    }

}
