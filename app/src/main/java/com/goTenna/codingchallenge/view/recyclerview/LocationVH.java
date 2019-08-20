package com.goTenna.codingchallenge.view.recyclerview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.LocationObject;
import com.goTenna.codingchallenge.view.mapbox.MapActivity;

public class LocationVH extends RecyclerView.ViewHolder {

    private TextView nameView;
    private TextView descriptionView;
    private CardView locationCard;

    public LocationVH(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.location_name);
        descriptionView = itemView.findViewById(R.id.location_description);
        locationCard = itemView.findViewById(R.id.location_card);
    }

    public void onBind(final LocationObject locationObject){
        String name = locationObject.getName();
        String description = locationObject.getDescription();

        nameView.setText(name);
        descriptionView.setText(description);
        locationCard.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MapActivity.class);
            intent.putExtra(MapActivity.LAT, locationObject.getLatitude());
            intent.putExtra(MapActivity.LNG, locationObject.getLongitude());
            v.getContext().startActivity(intent);
        });




    }
}
