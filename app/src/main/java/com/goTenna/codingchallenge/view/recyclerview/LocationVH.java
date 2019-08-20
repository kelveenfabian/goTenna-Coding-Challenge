package com.goTenna.codingchallenge.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.Location;

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

    public void onBind(Location location){
        String name = location.getName();
        String description = location.getDescription();

        nameView.setText(name);
        descriptionView.setText(description);
        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "I'm clickable!", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
