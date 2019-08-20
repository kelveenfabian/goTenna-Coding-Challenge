package com.goTenna.codingchallenge.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goTenna.codingchallenge.R;
import com.goTenna.codingchallenge.data.model.LocationObject;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationVH> {
    private List<LocationObject> locationObjectList;

    public LocationAdapter(List<LocationObject> locationObjectList){
        this.locationObjectList = locationObjectList;
    }

    @NonNull
    @Override
    public LocationVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_itemview, viewGroup, false);
        return new LocationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationVH holder, int position) {
        holder.onBind(locationObjectList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationObjectList.size();
    }
}
