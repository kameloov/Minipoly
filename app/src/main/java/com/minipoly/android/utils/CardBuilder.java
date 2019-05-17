package com.minipoly.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.minipoly.android.R;
import com.minipoly.android.entity.Country;

public class CardBuilder {

    private View card;
    private TextView txtName;
    private TextView txtFollowers;
    private TextView txtRCount;
    private TextView txtMCount;
    private TextView txtSales;

    public CardBuilder(LayoutInflater inflater) {
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

    private void inflateCard(LayoutInflater inflater){
        card =inflater.inflate(R.layout.dialog_country_card,null);
        txtName = card.findViewById(R.id.txtName);
        txtFollowers = card.findViewById(R.id.txtFollowers);
        txtRCount = card.findViewById(R.id.txtRCount);
        txtMCount = card.findViewById(R.id.txtMCount);
        txtSales = card.findViewById(R.id.txtSales);
    }
}
