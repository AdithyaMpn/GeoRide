package com.example.georide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MatchedUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AppCompatActivity activity;
    private List<MatchedUserModel> matchedUserModelList;

    public MatchedUserAdapter(AppCompatActivity activity, List<MatchedUserModel> matchedUserModelList) {
        this.activity = activity;
        this.matchedUserModelList = matchedUserModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matched_user, parent, false);
        return new VIHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((VIHolder) holder).userName.setText(matchedUserModelList.get(position).getUserName());
        ((VIHolder) holder).vehicleName.setText(matchedUserModelList.get(position).getVehicleName());
        ((VIHolder) holder).price.setText(matchedUserModelList.get(position).getPrice());
        ((VIHolder) holder).time.setText(matchedUserModelList.get(position).getTime());
        ((VIHolder) holder).pickup_distance.setText(matchedUserModelList.get(position).getPickup_distance());
        ((VIHolder) holder).drop_distance.setText(matchedUserModelList.get(position).getDrop_distance());
        ((VIHolder) holder).rating.setText(matchedUserModelList.get(position).getRating());

        Glide.with(activity)
                .load(matchedUserModelList.get(position).getImageUri())
                .centerCrop()
                .placeholder(R.drawable.ic_user_default)
                .into(((VIHolder) holder).userImage);
    }

    @Override
    public int getItemCount() {
        if (matchedUserModelList != null)
            return matchedUserModelList.size();
        else
            return 0;
    }

    private class VIHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName, vehicleName, price, time, pickup_distance, drop_distance, rating;

        VIHolder(final View itemLayoutView) {
            super(itemLayoutView);
            userImage = (ImageView) itemLayoutView.findViewById(R.id.userImage);
            userName = (TextView) itemLayoutView.findViewById(R.id.userName);
            vehicleName = (TextView) itemLayoutView.findViewById(R.id.vehicleName);
            price = (TextView) itemLayoutView.findViewById(R.id.price);
            time = (TextView) itemLayoutView.findViewById(R.id.time);
            pickup_distance = (TextView) itemLayoutView.findViewById(R.id.pickup_distance);
            drop_distance = (TextView) itemLayoutView.findViewById(R.id.drop_distance);
            rating = (TextView) itemLayoutView.findViewById(R.id.rating);

        }
    }
}
