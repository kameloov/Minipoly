package com.minipoly.android.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.minipoly.android.R;
import com.minipoly.android.RootFragment;
import com.minipoly.android.UserManager;
import com.minipoly.android.databinding.LoginFragmentBinding;

public class LoginFragment extends RootFragment {

    private LoginViewModel model;
    private LoginFragmentBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setM(model);
        binding.imgGoogle.setOnClickListener(v -> {
            model.loading.setValue(true);
            UserManager.loginGoogle(getActivity());
        });
        attachObservers();
    }

    private void attachObservers() {
        model.isRegistered().observe(this, aBoolean -> {
            if (aBoolean)
                showMap();
        });
    }

    private void showMap() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.login_to_home);
    }

}
