package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minipoly.android.R;
import com.minipoly.android.entity.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Image> images;
    private Image current;
    private View.OnClickListener listener;

    public ImageAdapter(View.OnClickListener addListener) {
        this.images = new ArrayList<>();
        images.add(new Image());
        this.listener = addListener;
    }

    public List<Image> getImages() {
        return images;
    }


    public void setImages(List<Image> images) {
        this.images.addAll(images);
        for (Image i : images)
            if (i.isIcon())
                current = i;
    }

    public void addImage(Image image) {
        images.add(image);
        // set the first added image as default image
        if (images.size() == 2) {
            current = image;
            current.setIcon(true);
        }
        notifyItemInserted(images.size() - 1);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_picture, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Image image;
        private ImageView img;
        private ImageView imgUpload;
        private ImageView txtDel;
        private ImageView txtDefault;
        private View bg;
        private boolean visible;

        public ImageViewHolder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.image);
            imgUpload = v.findViewById(R.id.imgUpload);
            txtDel = v.findViewById(R.id.btnDelete);
            txtDefault = v.findViewById(R.id.btnDefault);
            bg = v;
        }

        public void bind(int index) {
            this.image = images.get(index);
            if (index == 0)
                img.setImageResource(R.mipmap.add_image);
            else
                Glide.with(img.getContext()).load(image.getUri()).into(img);
            imgUpload.setVisibility(image.isUploaded() ? View.VISIBLE : View.GONE);
            bg.setBackgroundResource(image.isIcon() ? R.color.country_bg : R.color.white);
            txtDefault.setOnClickListener(this);
            txtDel.setOnClickListener(this);
            img.setOnClickListener(index == 0 ? listener : this);
            setVisibility();
        }

        private void setVisibility() {
            txtDel.setVisibility(visible ? View.VISIBLE : View.GONE);
            txtDefault.setVisibility(visible ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDefault:
                    visible = false;
                    current.setIcon(false);
                    current = image;
                    current.setIcon(true);
                    notifyDataSetChanged();
                    break;
                case R.id.btnDelete:
                    images.remove(image);
                    notifyDataSetChanged();
                    break;
                case R.id.image:
                    visible = !visible;
                    notifyItemChanged(getAdapterPosition());
                    break;
            }

        }
    }
}
