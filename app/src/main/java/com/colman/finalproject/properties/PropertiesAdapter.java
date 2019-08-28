package com.colman.finalproject.properties;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.finalproject.R;
import com.colman.finalproject.models.Property;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.PropertyViewHolder> {

    private Context mContext;
    private List<Property> mProperties;
    private OnItemClickListener mListener;

    PropertiesAdapter(Context context, List<Property> properties) {
        this.mProperties = properties;
        this.mContext = context;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.property_item_layout, viewGroup, false);
        return new PropertyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder propertyViewHolder, int i) {
        propertyViewHolder.numOfRooms.setText(mContext.getString(R.string.num_rooms, mProperties.get(i).getNumberOfRooms()));
        propertyViewHolder.floor.setText(mContext.getString(R.string.floor, mProperties.get(i).getFloor()));
        propertyViewHolder.price.setText(Html.fromHtml(mContext.getString(R.string.price, mProperties.get(i).getPrice())));
        propertyViewHolder.propertyType.setText(mProperties.get(i).getHouseType());
        propertyViewHolder.address.setText(mProperties.get(i).getAddress());

        Picasso.get()
                .load(mProperties.get(i).getImageUrl())
                .placeholder(R.drawable.img_placeholder)
                .into(propertyViewHolder.propertyImage);
    }

    @Override
    public int getItemCount() {
        return mProperties.size();
    }

    interface OnItemClickListener {
        void onClick(int position);
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {

        ImageView propertyImage;
        TextView price;
        TextView propertyType;
        TextView numOfRooms;
        TextView floor;
        TextView address;

        PropertyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onClick(position);
                    }
                }
            });

            propertyImage = itemView.findViewById(R.id.property_image);
            price = itemView.findViewById(R.id.price);
            propertyType = itemView.findViewById(R.id.property_type);
            numOfRooms = itemView.findViewById(R.id.num_rooms);
            floor = itemView.findViewById(R.id.floor);
            address = itemView.findViewById(R.id.address);

        }
    }
}
