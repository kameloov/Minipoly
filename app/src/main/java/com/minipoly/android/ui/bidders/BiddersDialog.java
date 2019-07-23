package com.minipoly.android.ui.bidders;

import android.os.Bundle;
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

import com.minipoly.android.adapter.BidAdapter;
import com.minipoly.android.databinding.BiddersDialogBinding;

public class BiddersDialog extends DialogFragment {

    private BiddersViewModel model;
    private BiddersDialogBinding binding;
    private BidAdapter adapter;

    public static BiddersDialog newInstance() {
        return new BiddersDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding = BiddersDialogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String auctionId = BiddersDialogArgs.fromBundle(getArguments()).getAuctionId();
        model = ViewModelProviders.of(this, new BiddersModelFactory(auctionId)).get(BiddersViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        binding.options.setVisibility(View.INVISIBLE);
        prepareAdapter();
        attachObservers();
    }

    private void prepareAdapter() {
        if (adapter == null)
            adapter = new BidAdapter(binding.options);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        binding.lstBids.setAdapter(adapter);
        binding.lstBids.setLayoutManager(manager);
    }

    private void attachObservers() {
        model.bids.observe(this, bids -> {
            if (bids != null)
                adapter.submitList(bids);
            else
                binding.txtEmpty.setVisibility(View.VISIBLE);
        });
    }

}
