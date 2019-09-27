package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
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

    private LifecycleOwner owner;

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

    public CommentAdapter(LifecycleOwner owner) {
        super(ITEM_CALLBACK);
        this.owner = owner;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemCommentBinding binding = ListItemCommentBinding.inflate(inflater);
        binding.setLifecycleOwner(owner);
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
        LayoutInflater inflater;

        public CommentHolder(@NonNull ListItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            inflater = LayoutInflater.from(binding.getRoot().getContext());
        }

        public void loadReplies(Comment c) {
            CommentRepository.loadReplies(c, (success, data) -> {
                if (success && data != null) {
                    binding.lstReplies.removeAllViews();
                    for (Reply r : data) {
                        ListItemReplyBinding b = ListItemReplyBinding.inflate(inflater);
                        b.setR(r);
                        binding.lstReplies.addView(b.getRoot());
                    }
                    showReplies.setValue(true);
                }
            });
        }

        public void showReply(View v) {
            PopupInput input = new PopupInput(binding.getRoot(), (positive, text) -> {
                if (positive && text.length() > 0)
                    reply(text, c);
            });
            input.show();
        }

        public void reply(String text, Comment c) {
            CommentRepository.addReply(text, c, success -> loadReplies(c));
        }

        public void bind(Comment comment) {
            this.c = comment;
            binding.setCh(this);
            binding.lstReplies.removeAllViews();
            if (comment.getReply() != null) {
                ListItemReplyBinding rb = ListItemReplyBinding.inflate(inflater);
                rb.setR(comment.getReply());
                binding.lstReplies.addView(rb.getRoot());
            }

            binding.txtShow.setOnClickListener(v -> loadReplies(c));
        }
    }
}
