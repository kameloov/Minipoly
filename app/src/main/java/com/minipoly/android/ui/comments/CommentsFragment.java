package com.minipoly.android.ui.comments;

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

import com.minipoly.android.adapter.CommentAdapter;
import com.minipoly.android.databinding.CommentsFragmentBinding;
import com.minipoly.android.entity.Realestate;

public class CommentsFragment extends Fragment {

    private CommentsViewModel model;
    private CommentsFragmentBinding binding;
    private CommentAdapter adapter = new CommentAdapter(this);

    public static CommentsFragment newInstance() {
        return new CommentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CommentsFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Realestate realestate = CommentsFragmentArgs.fromBundle(getArguments()).getRealestate();
        model = ViewModelProviders.of(this, new CommentsViewModelFactory(realestate)).get(CommentsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareAdapter();
        attachObservers();

    }

    private void prepareAdapter() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 1);
        binding.lstComments.setLayoutManager(manager);
        binding.lstComments.setAdapter(adapter);
    }

    private void attachObservers() {
        model.getComments().observe(this, comments -> adapter.submitList(comments));
    }

}
