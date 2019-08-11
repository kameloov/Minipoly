package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemCommentBinding;
import com.minipoly.android.databinding.ListItemReplyBinding;
import com.minipoly.android.entity.Comment;
import com.minipoly.android.entity.Reply;
import com.minipoly.android.popup.PopupInput;
import com.minipoly.android.repository.CommentRepository;

public class CommentAdapter extends ListAdapter<Comment, CommentAdapter.CommentHolder> {

    private static final DiffUtil.ItemCallback<Comment> ITEM_CALLBACK = new DiffUtil.ItemCallback<Comment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getText().equals(newItem.getText());
        }
    };

    public CommentAdapter() {
        super(ITEM_CALLBACK);
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

    public class CommentHolder extends RecyclerView.ViewHolder {
        private ListItemCommentBinding binding;
        public MutableLiveData<Boolean> showReplies = new MutableLiveData<>(false);
        public Comment c;
        public CommentHolder(@NonNull ListItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void loadReplies(Comment c) {
            CommentRepository.loadReplies(c, (success, data) -> {
                if (success && data != null) {
                    LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
                    for (Reply r : data) {
                        ListItemReplyBinding b = ListItemReplyBinding.inflate(inflater);
                        b.setR(r);
                        binding.lstReplies.addView(b.getRoot());
                    }
                }
            });
        }

        public void setShowReplies() {
            showReplies.setValue(true);
            notifyItemChanged(getAdapterPosition());
        }

        public void showReply(View v) {
            PopupInput input = new PopupInput(binding.getRoot(), (positive, text) -> {
                if (positive && text.length() > 0)
                    reply(text, c);
            });
            input.show();
        }

        public void reply(String text, Comment c) {
            CommentRepository.addReply(text, c, success -> {
            });
        }

        public void bind(Comment comment) {
            this.c = comment;
            binding.setCh(this);
            binding.lstReplies.removeAllViews();
            loadReplies(comment);

        }
    }
}
