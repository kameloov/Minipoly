package com.minipoly.android;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RootFragment extends Fragment {
    public RootFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideNav();
    }


    public void showNav() {
        ((MainActivity) getActivity()).setNavvisibility(true);
    }

    public void hideNav() {
        ((MainActivity) getActivity()).setNavvisibility(false);
    }
}
