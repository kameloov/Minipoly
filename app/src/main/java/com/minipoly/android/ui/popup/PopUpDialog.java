package com.minipoly.android.ui.popup;

import android.app.Dialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.maps.model.LatLng;
import com.minipoly.android.R;
import com.minipoly.android.databinding.DialogPopupBinding;

public class PopUpDialog extends DialogFragment {

    private PopUpViewModel vm;
    private DialogPopupBinding binding;
    private LatLng latLng;

    public static PopUpDialog newInstance(LatLng latLng){
        PopUpDialog dialog = new PopUpDialog();
        dialog.setLatLng(latLng);
        return  dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DialogPopupBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setTitle(getString(R.string.choose));
        vm = ViewModelProviders.of(this).get(PopUpViewModel.class);
        binding.setVm(vm);
        binding.txtAddMarket.setOnClickListener(v -> vm.addRealestate(latLng));
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
