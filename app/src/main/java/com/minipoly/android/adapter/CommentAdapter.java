package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemCommentBinding;
import com.minipoly.android.entity.Comment;

public class CommentAdapter extends ListAdapter<Comment, CommentAdapter.CommentHolder> {


    protected CommentAdapter(@NonNull DiffUtil.ItemCallback<Comment> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemCommentBinding binding = ListItemCommentBinding.inflate(inflater);
        return new CommentHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        private ListItemCommentBinding binding;

        public CommentHolder(@NonNull ListItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Comment comment) {
            binding.setC(comment);
        }
    }
}
