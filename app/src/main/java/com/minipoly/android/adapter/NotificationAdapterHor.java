package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemNotificationHorBinding;
import com.minipoly.android.entity.Notification;

public class NotificationAdapterHor extends ListAdapter<Notification, NotificationAdapterHor.NotificationHolder> {
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
    private int height = 0;

    public NotificationAdapterHor() {
        super(CATEGORY_ITEM_CALLBACK);
        this.height = height;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemNotificationHorBinding binding = ListItemNotificationHorBinding.inflate(inflater);
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
        private ListItemNotificationHorBinding binding;

        public NotificationHolder(@NonNull ListItemNotificationHorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Notification notification) {
            binding.setN(notification);
            int x = (int) (Math.random() * 14);
            binding.setT(x + " hour");
            binding.text.setVisibility(View.GONE);
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            binding.text.setVisibility(View.VISIBLE);
        }
    }
}
