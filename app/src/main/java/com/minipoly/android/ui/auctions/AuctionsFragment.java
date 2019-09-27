package com.minipoly.android.ui.auctions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.NavGraphDirections;
import com.minipoly.android.RootFragment;
import com.minipoly.android.adapter.AuctionAdapter;
import com.minipoly.android.databinding.AuctionsFragmentBinding;

public class AuctionsFragment extends RootFragment {

    private AuctionsViewModel model;
    private AuctionsFragmentBinding binding;
    private AuctionAdapter adapter = new AuctionAdapter();

    public static AuctionsFragment newInstance() {
        return new AuctionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AuctionsFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNav();
        model = ViewModelProviders.of(this).get(AuctionsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareAdapter();
        attachObservers();
    }

    private void prepareAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        binding.lstAuctions.setLayoutManager(manager);
        binding.lstAuctions.setAdapter(adapter);
        adapter.setListener(auction -> {
            NavGraphDirections.ActionAuctionDetails action =
                    NavGraphDirections.actionAuctionDetails(auction);
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
    }

    private void attachObservers() {
        model.auctions.observe(this, auctions -> {
            adapter.submitList(auctions);
        });
    }

}
