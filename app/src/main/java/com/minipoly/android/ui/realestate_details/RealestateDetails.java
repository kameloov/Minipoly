package com.minipoly.android.ui.realestate_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.R;
import com.minipoly.android.RootFragment;
import com.minipoly.android.adapter.CommentAdapter;
import com.minipoly.android.adapter.RemoteImageAdapter;
import com.minipoly.android.databinding.RealestateDetailsFragmentBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.repository.UserRepository;

import java.util.Date;

public class RealestateDetails extends RootFragment {

    private RealestateDetailsViewModel model;
    private RealestateDetailsFragmentBinding binding;
    private CommentAdapter commentAdapter = new CommentAdapter(this);
    private RemoteImageAdapter remoteImageAdapter;


    public static RealestateDetails newInstance() {
        return new RealestateDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RealestateDetailsFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Realestate realestate = RealestateDetailsArgs.fromBundle(getArguments()).getRealestate();
        model = ViewModelProviders.of(this, new RealestateDetailsModelFactory(realestate))
                .get(RealestateDetailsViewModel.class);
        prepareImageAdapter();
        prepareCommentsAdapter();
        binding.setLifecycleOwner(this);
        binding.setM(model);
        binding.setMyId(UserRepository.getUserId());
        binding.setNow(new Date());
        attachObservers();
    }

    private void attachObservers() {
        model.getComments().observe(this, comments -> commentAdapter.submitList(comments));
        model.watching.observe(this, aBoolean -> {
            if (model.isAdvrtOwner())
                return;
            Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
            binding.imgBell.startAnimation(a);
        });
    }

    private void prepareImageAdapter() {
        remoteImageAdapter = new RemoteImageAdapter(model.getRealestate().getValue().getImages(),
                model.currentImage);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.imgList.setLayoutManager(manager);
        binding.imgList.setAdapter(remoteImageAdapter);
    }

    private void prepareCommentsAdapter() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        binding.lstComments.setLayoutManager(manager);
        binding.lstComments.setAdapter(commentAdapter);
    }
}
