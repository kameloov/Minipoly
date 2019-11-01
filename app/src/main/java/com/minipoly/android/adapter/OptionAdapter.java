package com.minipoly.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.minipoly.android.databinding.ListItemOptionBinding;
import com.minipoly.android.entity.Option;

import java.util.List;

public class OptionAdapter extends BaseAdapter {

    private List<Option> options;

    public OptionAdapter(List<Option> options) {
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Option getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemOptionBinding binding = ListItemOptionBinding.inflate(inflater);
        binding.setAr(false);
        binding.setO(getItem(position));
        return binding.getRoot();
    }
}
