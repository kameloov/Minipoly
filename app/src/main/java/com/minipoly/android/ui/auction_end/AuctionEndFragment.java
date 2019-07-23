package com.minipoly.android.ui.auction_end;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.adapter.TopBiddersAdapter;
import com.minipoly.android.databinding.AutionEndFragmentBinding;

public class AuctionEndFragment extends Fragment {

    private AuctionEndViewModel model;
    private AutionEndFragmentBinding binding;
    private TopBiddersAdapter adapter = new TopBiddersAdapter();

    public static AuctionEndFragment newInstance() {
        return new AuctionEndFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AutionEndFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String auctionId = AuctionEndFragmentArgs.fromBundle(getArguments()).getAuctionId();
        model = ViewModelProviders.of(this, new AuctionEndModelFactory(auctionId)).get(AuctionEndViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareAdapter();
        attachObservers();
        // TODO: Use the ViewModel
    }

    private void prepareAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        binding.lstBids.setLayoutManager(manager);
        binding.lstBids.setAdapter(adapter);
    }

    private void attachObservers() {
        model.bids.observe(this, bids -> adapter.submitList(bids));
    }

}
