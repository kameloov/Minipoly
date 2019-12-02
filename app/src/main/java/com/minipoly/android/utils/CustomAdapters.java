package com.minipoly.android.utils;

import android.text.format.DateFormat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.StorageReference;
import com.minipoly.android.C;
import com.minipoly.android.R;
import com.minipoly.android.StorageManager;
import com.minipoly.android.adapter.OptionAdapter;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.Option;
import com.minipoly.android.num.ToeType;
import com.minipoly.android.repository.SocialRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomAdapters {
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    @BindingAdapter("avatar")
    public static void setAvatar(ImageView view, String image) {
        if (image == null || image.isEmpty()) {
            GlideApp.with(view.getContext()).load(R.drawable.circle)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fallback(R.drawable.circle)
                    .into(view);
            return;
        }
        StorageReference reference = StorageManager.getRoot().child(C.AVATARS_FOLDER).child(image);
        GlideApp.with(view.getContext())
                .load(reference).diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(new RequestOptions().circleCrop().error(R.drawable.circle))
                .into(view);
    }


    public static void setAdapter(Spinner spinner, List<Option> list) {
        if (list == null)
            return;
        OptionAdapter adapter = new OptionAdapter(list);
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("date")
    public static void setDate(TextView textView, Date date) {
        if (date != null)
            textView.setText(formatter.format(date));
    }

    @BindingAdapter("wall")
    public static void setWall(ImageView view, String image) {
        if (image == null || image.isEmpty())
            return;

        StorageReference reference = StorageManager.getRoot().child(C.WALLS_FOLDER).child(image);
        GlideApp.with(view.getContext())
                .load(reference).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }

    @BindingAdapter("glow")
    public static void setGlow(View v, boolean glow) {
        Animation a = v.getAnimation();
        if (glow) {
            if (a == null)
                a = AnimationUtils.loadAnimation(v.getContext(), R.anim.fade);
            v.startAnimation(a);
        } else {
            if (a != null)
                a.cancel();
            v.clearAnimation();
        }
        v.setVisibility(glow ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("toe")
    public static void setToe(ImageView img, int id) {
        if (id != 0)
            img.setImageResource(id);
    }

    @BindingAdapter("toebg")
    public static void setToeBg(View v, ToeType type) {
        if (type == null) {
            v.setBackgroundResource(R.drawable.toe_white_reverse);
            return;
        }

        switch (type) {
            case OFFER:
                v.setBackgroundResource(R.drawable.toe_red_reverse);
                break;
            case NORMAL:
                v.setBackgroundResource(R.drawable.toe_white_reverse);
                break;
            case AUCTION:
                v.setBackgroundResource(R.drawable.toe_blue_reverse);
                break;
        }
    }

    @BindingAdapter("image")
    public static void setImage(ImageView view, Image image) {
        if (image == null)
            return;
        StorageReference reference = StorageManager.getRoot().child(image.getUserId()).child(image.getId());
        GlideApp.with(view.getContext())
                .load(reference).placeholder(R.drawable.bar_frame).into(view);
    }


    @BindingAdapter("roundedImage")
    public static void setRoundedImage(ImageView view, Image image) {
        if (image == null)
            return;
        StorageReference reference = StorageManager.getRoot().child(image.getUserId()).child(image.getId());
        GlideApp.with(view.getContext()).load(reference)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(14))).into(view);
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
        view.setText(DateFormat.format("hh:mm", date));

    }

    @BindingAdapter({"advrt", "mode"})
    public static void setLiked(ImageView view, String advrt, String mode) {
        if (mode.equals("like")) {
            SocialRepository.liked(advrt, (success, data) -> {
                view.setImageResource(data ? R.drawable.like_selected : R.drawable.like);
            });
        } else if (mode.equals("dislike")) {
            SocialRepository.disliked(advrt, (success, data) -> {
                view.setImageResource(data ? R.drawable.dislike_selected : R.drawable.dislike);

            });
        }
    }

    @BindingAdapter("social")
    public static void setSocial(TextView textView, float value) {
        String s = String.valueOf(value);
        if (value >= 1000)
            s = value / 1000.0f + " K";
        if (value >= 1000000)
            s = value / 1000000.0f + " M";
        textView.setText(s);
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
