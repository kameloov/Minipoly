package com.minipoly.android.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.adapter.MessageAdapter;
import com.minipoly.android.databinding.ChatFragmentBinding;

public class ChatFragment extends Fragment {

    private ChatFragmentBinding binding;
    private ChatViewModel model;
    private MessageAdapter adapter = new MessageAdapter();

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ChatFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String conversationId = ChatFragmentArgs.fromBundle(getArguments()).getConversationId();
        model = ViewModelProviders.of(this, new ChatViewModelFactory(conversationId)).get(ChatViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(model);
        prepareAdapter();
        attachObservers();
    }

    private void prepareAdapter() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        binding.lstMesages.setLayoutManager(manager);
        binding.lstMesages.setAdapter(adapter);
    }

    private void attachObservers() {
        model.getMessages().observe(this, messages -> {
            adapter.submitList(messages);
            binding.lstMesages.scrollToPosition(messages.size() - 1);
        });
    }

}
