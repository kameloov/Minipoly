package com.minipoly.android.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.minipoly.android.R;

public class UserDealsFragment extends Fragment {

    private UserDealsViewModel userDealsViewModel;

    public static UserDealsFragment newInstance() {
        return new UserDealsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_deals_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userDealsViewModel = ViewModelProviders.of(this).get(UserDealsViewModel.class);
        // TODO: Use the ViewModel
    }

}
