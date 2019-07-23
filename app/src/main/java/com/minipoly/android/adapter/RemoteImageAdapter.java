package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.minipoly.android.databinding.ListItemRemoteImageBinding;
import com.minipoly.android.entity.Image;

import java.util.List;

public class RemoteImageAdapter extends RecyclerView.Adapter<RemoteImageAdapter.ImageViewHolder> {
    private List<Image> images;
    private MutableLiveData<Integer> currentIndex;
    public MutableLiveData<Image> current = new MutableLiveData<>();

    public RemoteImageAdapter(List<Image> images, MutableLiveData<Integer> currentIndex) {
        this.images = images;
        this.currentIndex = currentIndex;
        if (currentIndex.getValue() < images.size())
            this.current.setValue(images.get(currentIndex.getValue()));

    }

    public List<Image> getImages() {
        return images;
    }

    public void setCurrent(Image image) {
        current.setValue(image);
        currentIndex.setValue(images.indexOf(image));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemRemoteImageBinding binding = ListItemRemoteImageBinding.inflate(inflater);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        private ListItemRemoteImageBinding binding;

        public ImageViewHolder(@NonNull ListItemRemoteImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Image image) {
            binding.setImg(image);
            binding.setAdapter(RemoteImageAdapter.this);
        }

    }
}
