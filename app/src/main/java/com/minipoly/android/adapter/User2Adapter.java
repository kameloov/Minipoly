package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemUser2Binding;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.SocialUtils;

public class User2Adapter extends ListAdapter<UserBrief, User2Adapter.UserHolder> {
    private static DiffUtil.ItemCallback<UserBrief> DIFF_CALLbacK = new DiffUtil.ItemCallback<UserBrief>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserBrief oldItem, @NonNull UserBrief newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserBrief oldItem, @NonNull UserBrief newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    public User2Adapter() {
        super(DIFF_CALLbacK);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemUser2Binding binding = ListItemUser2Binding.inflate(inflater);
        return new UserHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        public MutableLiveData<Boolean> following = new MutableLiveData<>(false);
        public UserBrief brief;
        private ListItemUser2Binding binding;

        public UserHolder(@NonNull ListItemUser2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserBrief user) {
            brief = user;
            following.setValue(false);
            SocialRepository.isFollowing(brief.getId(), (success, data) -> {
                if (success) {
                    following.setValue(data);
                }
            });
            binding.setH(this);
        }

        public void toggleFollow() {
            if (following.getValue()) {
                SocialRepository.unFollow(brief.getId(), success -> {
                    if (success) {
                        following.setValue(false);
                        notifyItemChanged(getAdapterPosition());
                    }
                });

            } else {
                SocialRepository.follow(brief, success -> {
                    if (success) {
                        following.setValue(true);
                        notifyItemChanged(getAdapterPosition());
                    }
                });
            }
        }

        public void chat(View view) {
            SocialUtils.whatsappMsg(view.getContext(), brief.getPhone(), "Hello");
        }

    }

}
