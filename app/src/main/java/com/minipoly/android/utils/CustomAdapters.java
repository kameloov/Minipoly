package com.minipoly.android.utils;

import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.StorageReference;
import com.minipoly.android.C;
import com.minipoly.android.R;
import com.minipoly.android.StorageManager;
import com.minipoly.android.entity.Image;
import com.minipoly.android.repository.SocialRepository;

import java.util.Date;

public class CustomAdapters {

    private static DateFormat dateFormat = new DateFormat();

    @BindingAdapter("avatar")
    public static void setAvatar(ImageView view, String image) {
        if (image == null || image.isEmpty()) {
            GlideApp.with(view.getContext()).load(R.drawable.circle)

                    .into(view);
            return;
        }
        StorageReference reference = StorageManager.getRoot().child(C.AVATARS_FOLDER).child(image);
        GlideApp.with(view.getContext())
                .load(reference).apply(new RequestOptions().circleCrop().error(R.drawable.circle))
                .into(view);
    }

    @BindingAdapter("image")
    public static void setImage(ImageView view, Image image) {
        if (image == null)
            return;
        StorageReference reference = StorageManager.getRoot().child(image.getUserId()).child(image.getId());
        GlideApp.with(view.getContext())
                .load(reference).into(view);
    }

    @BindingAdapter("category")
    public static void setCategory(ImageView view, String id) {
        if (id == null)
            return;
        StorageReference reference = StorageManager.getRoot().child("category").child(id);
        GlideApp.with(view.getContext())
                .load(reference).into(view);
    }


    @BindingAdapter("time")
    public static void setTime(TextView view, Date date) {
        if (date == null)
            return;
        view.setText(DateFormat.format("hh : mm", date));

    }

    @BindingAdapter({"advrt", "mode"})
    public static void setLiked(ImageView view, String id, String mode) {
        if (mode.equals("like")) {
            SocialRepository.liked(id, (success, data) -> {
                view.setImageResource(data ? R.drawable.like_selected : R.drawable.like);
            });
        } else if (mode.equals("dislike")) {
            SocialRepository.disliked(id, (success, data) -> {
                view.setImageResource(data ? R.drawable.dislike_selected : R.drawable.dislike);

            });
        }
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
