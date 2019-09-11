package com.minipoly.android.ui.location_dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.adapter.CountryAdapter;
import com.minipoly.android.databinding.DialogLocationBinding;
import com.minipoly.android.entity.Country;

public class LocationDialog extends DialogFragment {

    private static LocationSelectListener listener;
    private static String countryId;
    private LocationDialogViewModel model;
    private DialogLocationBinding binding;
    private CountryAdapter adapter = new CountryAdapter();

    public static LocationDialog newInstance(String countryId, LocationSelectListener listener) {
        LocationDialog.listener = listener;
        LocationDialog.countryId = countryId;
        return new LocationDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        binding = DialogLocationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, new LocationViewModelFactory(countryId)).get(LocationDialogViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareAdapter();
        attachObservers();
    }

    private void prepareAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        adapter.setListener((b, country) -> {
            if (listener != null)
                listener.onCountrySelected(countryId != null, country);
            else
                Log.e("Listener", "listener is null");
            dismiss();
        });
        binding.lstCategory.setAdapter(adapter);
        binding.lstCategory.setLayoutManager(manager);
    }

    private void attachObservers() {
        model.getCountries().observe(this, categories -> {
            adapter.submitList(categories);
            Log.e("count", "count: " + categories.size());
            model.loading.setValue(false);
        });
    }

    public interface LocationSelectListener {
        void onCountrySelected(boolean city, Country country);
    }
}
