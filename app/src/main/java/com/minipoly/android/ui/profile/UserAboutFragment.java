package com.minipoly.android.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.adapter.ReviewAdapter;
import com.minipoly.android.adapter.User2Adapter;
import com.minipoly.android.databinding.UserAboutFragmentBinding;

public class UserAboutFragment extends Fragment {

    private UserAboutViewModel model;
    private UserAboutFragmentBinding binding;
    private String userId;
    private User2Adapter adapter = new User2Adapter();
    private ReviewAdapter reviewAdapter = new ReviewAdapter();

    public static UserAboutFragment newInstance(String userId) {
        UserAboutFragment fragment = new UserAboutFragment();
        fragment.userId = userId;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = UserAboutFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this, new AboutViewModelFactory(userId)).get(UserAboutViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareUserAdapter();
        prepareReviewAdapter();
        attachObservers();

    }

    private void prepareUserAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        binding.lstUsers.setLayoutManager(manager);
        binding.lstUsers.setAdapter(adapter);
    }

    private void prepareReviewAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        binding.lstReviews.setLayoutManager(manager);
        binding.lstReviews.setAdapter(reviewAdapter);
    }

    private void attachObservers() {
        model.users.observe(this, userBriefs -> {
            Log.e("attachObservers: ", "count is " + userBriefs.size());
            adapter.submitList(userBriefs);
        });
        model.reviews.observe(this, reviews -> reviewAdapter.submitList(reviews));

    }

}
