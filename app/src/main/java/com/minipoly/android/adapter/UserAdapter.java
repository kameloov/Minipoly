package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemUserBinding;
import com.minipoly.android.entity.User;
import com.minipoly.android.entity.UserBrief;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserHolder> {

    private IUserBriefList briefList;
    private static DiffUtil.ItemCallback<User> DIFF_CALLbacK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    public UserAdapter(IUserBriefList briefList) {
        super(DIFF_CALLbacK);
        this.briefList = briefList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemUserBinding binding = ListItemUserBinding.inflate(inflater);
        return new UserHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

    }

    public class UserHolder extends RecyclerView.ViewHolder {

        private ListItemUserBinding binding;
        public UserBrief brief;

        public UserHolder(@NonNull ListItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User user) {
            brief = new UserBrief(user);

        }

        public void select() {
            if (briefList != null)
                briefList.toggle(brief);
        }
    }

    public interface IUserBriefList {
        void toggle(UserBrief brief);
    }
}
