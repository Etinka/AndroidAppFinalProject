package com.colman.finalproject.properties;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colman.finalproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class PropertyImagesAdapter extends PagerAdapter {

    private List<String> propertyImages;
    private LayoutInflater inflater;

    PropertyImagesAdapter(Context context, List<String> propertyImages) {
        this.propertyImages = new ArrayList<>(propertyImages)   ;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return propertyImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.property_image, container, false);
        AppCompatImageView image = itemView.findViewById(R.id.image);
        String imageUrl = propertyImages.get(position);

        Picasso.get().load(imageUrl).into(image);

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
