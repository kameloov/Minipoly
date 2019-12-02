package com.minipoly.android.adapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.storage.StorageReference;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.minipoly.android.R;
import com.minipoly.android.StorageManager;
import com.minipoly.android.databinding.ListItemAdvrt1Binding;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.Toe;
import com.minipoly.android.num.ToeType;
import com.minipoly.android.param_managers.CarManager;
import com.minipoly.android.param_managers.ComputerManager;
import com.minipoly.android.param_managers.MobileManager;
import com.minipoly.android.param_managers.RealestateManager;
import com.minipoly.android.utils.GlideApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;

public class RealestateAdapterHor extends ListAdapter<Realestate, RealestateAdapterHor.RelestateViewHolder> {

    private static final DiffUtil.ItemCallback<Realestate> CATEGORY_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Realestate>() {

                @Override
                public boolean areItemsTheSame(@NonNull Realestate oldItem, @NonNull Realestate newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Realestate oldItem, @NonNull Realestate newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }
            };
    private static float TOE_LENGTH = .3f;
    private static float CIRCLE_RADIUS = .4f;
    private int itemHeight;
    private float ratio = 1.5f;
    private IRealestateListener listener;

    public RealestateAdapterHor() {
        super(CATEGORY_ITEM_CALLBACK);
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @NonNull
    @Override
    public RelestateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ListItemAdvrt1Binding binding = ListItemAdvrt1Binding.inflate(inflater);
        return new RelestateViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull RelestateViewHolder relestateViewHolder, int i) {
        relestateViewHolder.bind(getItem(i));
    }

    public void setListener(IRealestateListener listener) {
        this.listener = listener;
    }


    public interface IRealestateListener {
        void onRealestateSelected(Realestate realestate);
    }

    public class RelestateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemAdvrt1Binding binding;

        public RelestateViewHolder(@NonNull ListItemAdvrt1Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Realestate realestate) {
            binding.setAd(realestate);
            binding.setArabic(false);
            binding.setNow(new Date());
            binding.getRoot().setOnClickListener(this);
            int w = (int) (itemHeight / ratio);
            ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(w, itemHeight);
            binding.getRoot().setLayoutParams(params);
            if (binding.constraint.getLayoutParams() != null) {
                binding.constraint.getLayoutParams().width = w;
                binding.constraint.getLayoutParams().height = itemHeight;
            } else
                binding.constraint.setLayoutParams(params);
            binding.imageView94.getLayoutParams().width = w;
            binding.imageView94.getLayoutParams().height = itemHeight;
            binding.getRoot().invalidate();
            setImageAndBlur(realestate.getImages().get(0), binding.imageView94, binding.priceCircle, R.drawable.price_circle);
            setImageAndBlur(realestate.getImages().get(0), binding.imageView94, binding.imgToe, R.drawable.toe_mask);
            setImageAndBlur(realestate.getImages().get(0), binding.imageView94, binding.imgToe2, R.drawable.toe_mask_reverse);
            List<Toe> list = new ArrayList<>();
            ToeType type = isOffer(realestate) ? ToeType.OFFER : ToeType.NORMAL;
            if (realestate.isMarket()) {
                if (realestate.getCategoryId().equalsIgnoreCase("car"))
                    list = CarManager.getToes(realestate.getCarInfo(), type);
                if (realestate.getCategoryId().equalsIgnoreCase("mobile"))
                    list = MobileManager.getToes(realestate.getMobileInfo(), type);
                if (realestate.getCategoryId().equalsIgnoreCase("computer"))
                    list = ComputerManager.getToes(realestate.getComputerInfo(), type);

            } else
                list = RealestateManager.getToes(realestate.getRealestateInfo(), type);
            binding.setLst(list);
            String red;
            if (realestate.isMarket())
                red = binding.getRoot().getContext()
                        .getString(realestate.isUsed() ? R.string.used_tag : R.string.new_tag);
            else
                red = binding.getRoot().getContext()
                        .getString(realestate.getRealestateInfo().isRent() ? R.string.rent : R.string.sell);
            binding.setRed(red);
        }

        private void setImageAndBlur(Image image, ImageView bgImage, ImageView target, int maskID) {
            StorageReference reference = StorageManager.getRoot().child(image.getUserId()).child(image.getId());
            GlideApp.with(bgImage.getContext()).asBitmap()
                    .load(reference).into(new CustomTarget<Bitmap>((int) (itemHeight / ratio), itemHeight) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    bgImage.setImageBitmap(resource);
                    float ratio = resource.getHeight() * 1.0f / itemHeight;
                    int x = target.getLeft() - bgImage.getLeft();
                    int y = target.getTop() - bgImage.getTop();
                    int w = target.getWidth();
                    int h = target.getHeight();
                    Bitmap b = BitmapUtils.createBitmapFromView(bgImage);
                    if (b == null)
                        return;
                    if (x + w > b.getWidth())
                        w = b.getWidth() - x;
                    if ((y + h > b.getHeight()))
                        h = b.getHeight() - y;
                    String log = "image : " + b.getWidth() + "," + b.getHeight();
                    log += "view : " + target.getWidth() + "," + target.getHeight();
                    log += "item : " + x + "," + y + "," + w + "," + h;
                    Log.e("report", log);
                    b = Bitmap.createBitmap(b, x, y, w, h);
                    BitmapTransformation transformation = new MaskTransformation(maskID);
                    BlurTransformation blur = new BlurTransformation(25, 1);
                    GlideApp.with(bgImage.getContext()).load(b).transform(transformation, blur).into(target);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
        }

        public Bitmap loadBitmapFromView(View v) {
            Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.draw(c);
            return b;
        }

        private boolean isOffer(Realestate r) {
            Date now = new Date();
            return r.getOfferEnd() != null && r.getOfferEnd().after(now);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onRealestateSelected(binding.getAd());
        }
    }
}
