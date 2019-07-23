package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minipoly.android.R;
import com.minipoly.android.entity.Image;
import com.minipoly.android.utils.ImageBuffer;

public class ImageAdapter extends ListAdapter<Image, ImageAdapter.ImageViewHolder> {
    private Image current;
    private View.OnClickListener listener;
    private static DiffUtil.ItemCallback<Image> DIFF_CALLBACK = new DiffUtil.ItemCallback<Image>() {

        @Override
        public boolean areItemsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return oldItem.getId().equals(newItem.getId()) && oldItem.isUploaded() == newItem.isUploaded();
        }
    };

    public ImageAdapter(View.OnClickListener addListener) {
        super(DIFF_CALLBACK);
        this.listener = addListener;
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
            this.image = getItem(index);
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
                    ImageBuffer.removeImage(image);
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
