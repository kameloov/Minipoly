package com.minipoly.android.ui.newadvrt;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minipoly.android.R;
import com.minipoly.android.entity.Realestate;

public class AddAdvrtDialog extends DialogFragment {

    private AddAdvrtDialogViewModel model;

    public static AddAdvrtDialog newInstance() {
        return new AddAdvrtDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_advrt_dialog, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(AddAdvrtDialogViewModel.class);
        Realestate realestate = AddAdvrtDialogArgs.fromBundle(getArguments()).getItem();
        if (realestate!=null) {
            realestate.setArea(200);
            realestate.setText("شقة لايجار في موقع راقي مفروشة شرفة وحراسة ، طابق رابع يوجد مصعد  ");
            realestate.setTitle("شقة للايجار ");
            model.setRealestate(realestate);
        }
        // TODO: Use the ViewModel
    }

}
