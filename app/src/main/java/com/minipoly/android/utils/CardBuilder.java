package com.minipoly.android.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.minipoly.android.R;
import com.minipoly.android.databinding.AdMiniBinding;
import com.minipoly.android.entity.Country;
import com.minipoly.android.entity.Realestate;

public class CardBuilder {

    private View card;
    private TextView txtName;
    private TextView txtFollowers;
    private TextView txtRCount;
    private TextView txtMCount;
    private TextView txtSales;
    private LayoutInflater inflater;

    public CardBuilder(LayoutInflater inflater) {
        this.inflater = inflater;
        inflateCard(inflater);

    }

    public Bitmap generateCard(Country country, float zoom){
        card.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        txtName.setText(country.getName());
        txtFollowers.setText(country.getFollowers()+"");
        txtMCount.setText(country.getDealsCount()+"");
        txtRCount.setText(country.getRealestateCount()+"");
        Bitmap b = Bitmap.createBitmap( card.getMeasuredWidth(), card.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        card.layout(0, 0, card.getMeasuredWidth(), card.getMeasuredHeight());
        card.draw(c);
        b = Bitmap.createScaledBitmap(b, (int) (b.getWidth()*zoom*0.05f),
                (int) (b.getHeight()*zoom*0.05f),true);
        return b;
    }

    public Bitmap generatePin(Realestate realestate) {
        AdMiniBinding binding = AdMiniBinding.inflate(inflater);
        binding.setI(realestate.getImages().get(0));
        binding.setT(realestate.getPrice() + "");
        View v = binding.getRoot();
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.draw(c);
        return b;
    }

    private void inflateCard(LayoutInflater inflater){
        card =inflater.inflate(R.layout.dialog_country_card,null);
        txtName = card.findViewById(R.id.txtName);
        txtFollowers = card.findViewById(R.id.txtFollowers);
        txtRCount = card.findViewById(R.id.txtRCount);
        txtMCount = card.findViewById(R.id.txtMCount);
        txtSales = card.findViewById(R.id.txtSales);
    }
}
