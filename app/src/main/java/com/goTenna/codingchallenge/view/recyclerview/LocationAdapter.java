package com.goTenna.codingchallenge.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.Location;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationVH> {
    private List<Location> locationList;

    public LocationAdapter(List<Location> locationList){
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_itemview, viewGroup, false);
        return new LocationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationVH holder, int position) {
        holder.onBind(locationList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}
