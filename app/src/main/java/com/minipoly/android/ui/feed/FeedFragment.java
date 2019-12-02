package com.minipoly.android.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.adapter.NotificationAdapterHor;
import com.minipoly.android.adapter.RealestateAdapterHor;
import com.minipoly.android.databinding.FeedFragmentBinding;
import com.minipoly.android.decorations.SpacesItemDecoration;

public class FeedFragment extends Fragment {

    private FeedViewModel model;
    private FeedFragmentBinding binding;
    private RealestateAdapterHor followAdapter = new RealestateAdapterHor();
    private RealestateAdapterHor nearAdapter = new RealestateAdapterHor();
    private NotificationAdapterHor notificationAdapter = new NotificationAdapterHor();

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FeedFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(FeedViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareFollowingList();
        prepareDiscoverList();
        prepareNotificationList();
        attachObservers();
    }

    private void prepareFollowingList() {
        binding.lstFollowing.post(() -> {
            followAdapter.setItemHeight(binding.lstFollowing.getMeasuredHeight());
            followAdapter.setRatio(1.2f);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
            binding.lstFollowing.setLayoutManager(manager);
            binding.lstFollowing.addItemDecoration(new SpacesItemDecoration(8));
            binding.lstFollowing.setAdapter(followAdapter);
            binding.lstFollowing.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (manager.findLastCompletelyVisibleItemPosition() > manager.getItemCount() - 4)
                        model.loadMoreFollow();
                }
            });
        });
    }

    private void prepareDiscoverList() {
        binding.lstNear.post(() -> {
            int w = binding.lstNear.getMeasuredWidth() - 32;
            int h = (int) ((w / 2) * 1.5f);
            nearAdapter.setItemHeight(h);
            GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
            binding.lstNear.setLayoutManager(manager);
            binding.lstNear.addItemDecoration(new SpacesItemDecoration(8));
            binding.lstNear.setAdapter(nearAdapter);
            binding.lstNear.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (manager.findLastCompletelyVisibleItemPosition() > manager.getItemCount() - 4)
                        model.loadMoreDiscover();
                }
            });
        });
    }

    private void prepareNotificationList() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.lstNotification.setLayoutManager(manager);
        binding.lstNotification.addItemDecoration(new SpacesItemDecoration(4));
        binding.lstNotification.setAdapter(notificationAdapter);
        binding.lstNotification.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
               /* if (manager.findLastCompletelyVisibleItemPosition() > manager.getItemCount() - 4)
                    model.loadMoreDiscover();*/
            }
        });
    }

    private void attachObservers() {
        model.near.observe(getViewLifecycleOwner(), realestates -> {
            nearAdapter.submitList(realestates);
            model.setTempNotifications();
        });
        model.follow.observe(getViewLifecycleOwner(), realestates -> followAdapter.submitList(realestates));
        // todo switch to real notificationgs
        model.temp.observe(getViewLifecycleOwner(), notifications -> {
            if (notifications != null)
                notificationAdapter.submitList(notifications);
        });
    }

}
