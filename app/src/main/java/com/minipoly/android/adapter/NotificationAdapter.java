package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemNotificationBinding;
import com.minipoly.android.entity.Notification;

public class NotificationAdapter extends ListAdapter<Notification, NotificationAdapter.NotificationHolder> {

    private static final DiffUtil.ItemCallback<Notification> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Notification>() {

                @Override
                public boolean areItemsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Notification oldItem, @NonNull Notification newItem) {
                    return oldItem.getText().equals(newItem.getText());
                }
            };
    private NotificationSelected listener;

    public NotificationAdapter(NotificationSelected listener) {
        super(CATEGORY_ITEM_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemNotificationBinding binding = ListItemNotificationBinding.inflate(inflater);
        return new NotificationHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder notificationHolder, int i) {
        notificationHolder.bind(getItem(i));
    }


    public interface NotificationSelected {
        void onNotificationSelected(Notification notification);
    }

    public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemNotificationBinding binding;

        public NotificationHolder(@NonNull ListItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Notification notification) {
            binding.setN(notification);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onNotificationSelected(binding.getN());
        }
    }
}
