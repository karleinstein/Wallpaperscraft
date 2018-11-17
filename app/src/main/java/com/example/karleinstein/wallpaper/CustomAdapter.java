package com.example.karleinstein.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class CustomAdapter extends BaseAdapter {
    ArrayList<HinhAnh> hinhAnhs;
    Context context;

    public CustomAdapter(ArrayList<HinhAnh> hinhAnhs, Context context) {
        this.hinhAnhs = hinhAnhs;
        this.context = context;
    }

    public CustomAdapter() {

    }

    @Override
    public int getCount() {
        return hinhAnhs.size();
    }

    @Override
    public Object getItem(int position) {

        return hinhAnhs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_grid, parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgHinhAnh = convertView.findViewById(R.id.imgHinhAnh);
        final HinhAnh hinhAnh = (HinhAnh) getItem(position);
        RequestOptions options = new RequestOptions();
//        AnimationDrawable animationDrawable= (AnimationDrawable) ContextCompat.getDrawable(context,R.drawable.loading);
//        animationDrawable.start();
        options.placeholder(R.drawable.blocks);
        Glide.with(context).load(hinhAnh.hinhAnhz).apply(options).into(viewHolder.imgHinhAnh);
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgHinhAnh;
    }
}
