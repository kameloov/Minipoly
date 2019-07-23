package com.minipoly.android.ui.users_list;

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

import com.minipoly.android.adapter.UserAdapter;
import com.minipoly.android.databinding.UserListFragmentBinding;

public class UserListFragment extends Fragment {

    private UserListViewModel model;
    private UserListFragmentBinding binding;
    private UserAdapter adapter;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = UserListFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(UserListViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareAdapter();
        attachObservers();
        // TODO: Use the ViewModel
    }

    private void prepareAdapter() {
        adapter = new UserAdapter(model);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        binding.lstUsers.setLayoutManager(manager);
        binding.lstUsers.setAdapter(adapter);
    }

    private void attachObservers() {
        model.users.observe(this, users -> {
            adapter.submitList(users);
            binding.progressBar9.setVisibility(View.INVISIBLE);
        });
    }

}
