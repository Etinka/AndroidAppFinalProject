package com.colman.finalproject.properties;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.colman.finalproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PropertyImagesAdapter extends PagerAdapter {

    private List<String> mPropertyImages;

    PropertyImagesAdapter(List<String> propertyImages) {
        this.mPropertyImages = new ArrayList<>(propertyImages)   ;
    }

    @Override
    public int getCount() {
        return mPropertyImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.property_image, container, false);
        AppCompatImageView image = itemView.findViewById(R.id.image);
        String imageUrl = mPropertyImages.get(position);

        Picasso.get().load(imageUrl).placeholder(R.drawable.img_placeholder).into(image);

        container.addView(itemView, container.getChildCount());

        return itemView;
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }
}
