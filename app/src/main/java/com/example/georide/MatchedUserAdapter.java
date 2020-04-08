package com.example.georide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MatchedUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AppCompatActivity activity;
    private List<MatchedUserModel> matchedUserModelList;
    private MatchedUserReceiver matchedUserReceiver;

    MatchedUserAdapter(AppCompatActivity activity, List<MatchedUserModel> matchedUserModelList,MatchedUserReceiver matchedUserReceiver) {
        this.activity = activity;
        this.matchedUserModelList = matchedUserModelList;
        this.matchedUserReceiver = matchedUserReceiver;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matched_user, parent, false);
        return new VIHolder(userLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(matchedUserModelList!=null) {
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

            ((VIHolder) holder).matcheduser_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchedUserReceiver.onClickDetailView(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("matchedUserModelList", matchedUserModelList.get(position));
                    RideDetailedViewFragment rideDetailedViewFragment = new RideDetailedViewFragment();
                    rideDetailedViewFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, rideDetailedViewFragment).addToBackStack(null).commit();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (matchedUserModelList != null)
            return matchedUserModelList.size();
        else
            return 0;
    }

    private class VIHolder extends RecyclerView.ViewHolder {

        ConstraintLayout matcheduser_item;
        ImageView userImage;
        TextView userName, vehicleName, price, time, pickup_distance, drop_distance, rating;

        VIHolder(final View itemLayoutView) {
            super(itemLayoutView);
            matcheduser_item = itemLayoutView.findViewById(R.id.matcheduser_item);
            userImage = itemLayoutView.findViewById(R.id.userImage);
            userName = itemLayoutView.findViewById(R.id.userName);
            vehicleName = itemLayoutView.findViewById(R.id.vehicleName);
            price = itemLayoutView.findViewById(R.id.price);
            time = itemLayoutView.findViewById(R.id.time);
            pickup_distance = itemLayoutView.findViewById(R.id.pickup_distance);
            drop_distance = itemLayoutView.findViewById(R.id.drop_distance);
            rating = itemLayoutView.findViewById(R.id.rating);
        }
    }

    public interface MatchedUserReceiver {
        void onClickDetailView(int position);
    }
}
