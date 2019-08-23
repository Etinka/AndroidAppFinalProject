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

    private LayoutInflater inflater;
    private Context context;
    private List<Property> properties;
    private OnItemClickListener mListener;

    PropertiesAdapter(Context context, List<Property> properties) {
        this.inflater = LayoutInflater.from(context);
        this.properties = properties;
        this.context = context;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.property_item_layout, viewGroup, false);
        return new PropertyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder propertyViewHolder, int i) {
        propertyViewHolder.numOfRooms.setText(context.getString(R.string.num_rooms, properties.get(i).getNumberOfRooms()));
        propertyViewHolder.floor.setText(context.getString(R.string.floor, properties.get(i).getFloor()));
        propertyViewHolder.price.setText(Html.fromHtml(context.getString(R.string.price, properties.get(i).getPrice())));
        propertyViewHolder.propertyType.setText(properties.get(i).getHouseType());
        propertyViewHolder.address.setText(properties.get(i).getAddress());

        Picasso.get()
                .load(properties.get(i).getImageUrl())
                .placeholder(R.drawable.img_placeholder)
                .into(propertyViewHolder.propertyImage);
    }

    @Override
    public int getItemCount() {
        return properties.size();
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
