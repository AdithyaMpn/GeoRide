package com.example.georide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class RideDetailedViewFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rideView;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            rideView = inflater.inflate(R.layout.ride_detailed_view, container, false);
            MatchedUserModel matchedUserModel = (MatchedUserModel) bundle.getSerializable("matchedUserModelList");

            SupportMapFragment mapFragment = null;
            if (getFragmentManager() != null) {
                mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
            }
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }

            if (matchedUserModel != null && getActivity() != null) {
                TextView userName = rideView.findViewById(R.id.userName);
                userName.setText(matchedUserModel.getUserName());
                TextView vehicleName = rideView.findViewById(R.id.vehicleName);
                vehicleName.setText(matchedUserModel.getVehicleName());
                TextView price = rideView.findViewById(R.id.price);
                price.setText(matchedUserModel.getPrice());
                TextView time = rideView.findViewById(R.id.time);
                time.setText(matchedUserModel.getTime());

                ImageView userImage = rideView.findViewById(R.id.userImage);
                Glide.with(getActivity())
                        .load(matchedUserModel.getImageUri())
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_default)
                        .into(userImage);
            }

            TextView cancel_button = rideView.findViewById(R.id.cancel_button);
            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getFragmentManager() != null && getActivity() != null) {
                        getFragmentManager().popBackStack();
                        getActivity().findViewById(R.id.route_distance_layout).setVisibility(View.GONE);
                        getActivity().findViewById(R.id.matcheduser_recyclerview).setVisibility(View.VISIBLE);
                    }
                }
            });

        } else {
            rideView = inflater.inflate(R.layout.empty_layout, container, false);
        }
        return rideView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
