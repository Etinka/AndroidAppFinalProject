package com.colman.finalproject.properties;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colman.finalproject.R;
import com.colman.finalproject.models.Property;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.PropertyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Property> properties;

    public PropertiesAdapter(Context context, List<Property> properties) {
        this.inflater = LayoutInflater.from(context);
        this.properties = properties;
        this.context = context;
    }


    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.property_item_layout, viewGroup, false);
        return new PropertyViewHolder(view);
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
                .into(propertyViewHolder.propertyImage);

    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder {

        ImageView propertyImage;
        TextView price;
        TextView propertyType;
        TextView numOfRooms;
        TextView floor;
        TextView address;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyImage = itemView.findViewById(R.id.property_image);
            price = itemView.findViewById(R.id.price);
            propertyType = itemView.findViewById(R.id.property_type);
            numOfRooms = itemView.findViewById(R.id.num_rooms);
            floor = itemView.findViewById(R.id.floor);
            address = itemView.findViewById(R.id.address);

        }
    }
}
