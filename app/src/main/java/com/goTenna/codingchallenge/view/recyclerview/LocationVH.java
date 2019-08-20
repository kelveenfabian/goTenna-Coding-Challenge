package com.goTenna.codingchallenge.view.recyclerview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.Location;
import com.goTenna.codingchallenge.view.MapActivity;

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

    public void onBind(final Location location){
        String name = location.getName();
        String description = location.getDescription();

        nameView.setText(name);
        descriptionView.setText(description);
        locationCard.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MapActivity.class);
            intent.putExtra(MapActivity.LAT, location.getLatitude());
            intent.putExtra(MapActivity.LNG, location.getLongitude());
            v.getContext().startActivity(intent);
        });




    }
}
