package com.minipoly.android.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.StorageReference;
import com.minipoly.android.C;
import com.minipoly.android.R;
import com.minipoly.android.StorageManager;
import com.minipoly.android.entity.Image;

public class CustomAdapters {
    @BindingAdapter("avatar")
    public static void setAvatar(ImageView view, String image) {
        StorageReference reference = StorageManager.getRoot().child(C.AVATARS_FOLDER).child(image);
        GlideApp.with(view.getContext())
                .load(reference).apply(new RequestOptions().circleCrop())
                .into(view);
    }

    @BindingAdapter("image")
    public static void setImage(ImageView view, Image image) {
        StorageReference reference = StorageManager.getUserRoot().child(image.getId());
        GlideApp.with(view.getContext())
                .load(reference).into(view);
    }

    @BindingAdapter("stars")
    public static void setStars(ImageView img, int stars) {
        switch (stars) {
            case 0:
                img.setImageResource(R.mipmap.star0);
                break;
            case 1:
                img.setImageResource(R.mipmap.star1);
                break;
            case 2:
                img.setImageResource(R.mipmap.star2);
                break;
            case 3:
                img.setImageResource(R.mipmap.star3);
                break;
            case 4:
                img.setImageResource(R.mipmap.star4);
                break;
            case 5:
                img.setImageResource(R.mipmap.star5);
                break;
        }
    }
}
