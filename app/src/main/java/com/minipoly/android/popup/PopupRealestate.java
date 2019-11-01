package com.minipoly.android.popup;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.minipoly.android.ActivityViewModel;
import com.minipoly.android.NavGraphDirections;
import com.minipoly.android.R;
import com.minipoly.android.databinding.AdvrtPopBinding;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.Toe;
import com.minipoly.android.num.ToeType;
import com.minipoly.android.param_managers.RealestateManager;

import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class PopupRealestate extends PopupWindow {

    public PopupRealestate(View content, Realestate realestate) {
        super(content.getContext());
        LayoutInflater inflater = LayoutInflater.from(content.getContext());
        AdvrtPopBinding binding = AdvrtPopBinding.inflate(inflater);
        bind(binding, realestate);
        setContentView(binding.getRoot());
        setFocusable(true);
        setOutsideTouchable(true);
        binding.getRoot().setOnClickListener(v -> {
            NavGraphDirections.ActionGlobalRealestateDetails action =
                    NavGraphDirections.actionGlobalRealestateDetails(realestate);
            ActivityViewModel.getNavController().navigate(action);
            dismiss();
        });
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        Timber.e("pop up card_in");
        setAnimationStyle(R.style.Card);
    }

    private void bind(AdvrtPopBinding binding, Realestate realestate) {
        binding.setArabic(false);
        binding.setAd(realestate);
        List<Toe> list;
        ToeType type = isOffer(realestate) ? ToeType.OFFER : ToeType.NORMAL;
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
        setAnimationStyle(R.style.Card);
    }


    private boolean isOffer(Realestate r) {
        Date now = new Date();
        return r.getOfferEnd() != null && r.getOfferEnd().after(now);
    }

}
