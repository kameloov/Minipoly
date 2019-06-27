package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemReplyBinding;
import com.minipoly.android.entity.Reply;
import com.minipoly.android.utils.LocalData;

public class ReplyAdapter extends ListAdapter<Reply, ReplyAdapter.ReplyHolder> {
    private String userId = LocalData.getUserInfo().getId();
    private static final DiffUtil.ItemCallback<Reply> DIFF_CALLBACK = new DiffUtil.ItemCallback<Reply>() {

        @Override
        public boolean areItemsTheSame(@NonNull Reply oldItem, @NonNull Reply newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reply oldItem, @NonNull Reply newItem) {
            return oldItem.getText().equals(newItem.getText());
        }
    };

    public ReplyAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public ReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemReplyBinding binding = ListItemReplyBinding.inflate(inflater);
        return new ReplyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ReplyHolder extends RecyclerView.ViewHolder {
        ListItemReplyBinding binding;

        public ReplyHolder(@NonNull ListItemReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Reply reply) {
            binding.setR(reply);
        }
    }
}
