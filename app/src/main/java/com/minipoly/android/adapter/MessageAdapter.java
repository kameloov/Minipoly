package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemMessageBinding;
import com.minipoly.android.entity.Message;
import com.minipoly.android.utils.LocalData;

public class MessageAdapter extends ListAdapter<Message, MessageAdapter.MessageHolder> {
    private String userId = LocalData.getUserInfo().getId();
    private static final DiffUtil.ItemCallback<Message> DIFF_CALLBACK = new DiffUtil.ItemCallback<Message>() {
        @Override
        public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
            return oldItem.getText().equals(newItem.getText());
        }
    };

    public MessageAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemMessageBinding binding = ListItemMessageBinding.inflate(inflater);
        return new MessageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        ListItemMessageBinding binding;

        public MessageHolder(@NonNull ListItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Message message) {
            binding.setM(message);
            binding.setUserId(userId);
        }
    }
}
