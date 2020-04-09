package com.example.georide;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class RideDetailedViewFragment extends Fragment implements OnMapReadyCallback {

    private static String TAG = RideDetailedViewFragment.class.getSimpleName();

    private LatLng start,end;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rideView;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            rideView = inflater.inflate(R.layout.ride_detailed_view, container, false);
            MatchedUserModel matchedUserModel = (MatchedUserModel) bundle.getSerializable("matchedUserModelList");

            if (matchedUserModel != null && getActivity() != null) {
                TextView userName = rideView.findViewById(R.id.userName);
                userName.setText(matchedUserModel.getUserName());
                TextView vehicleName = rideView.findViewById(R.id.vehicleName);
                vehicleName.setText(matchedUserModel.getVehicleName());
                TextView time = rideView.findViewById(R.id.time);
                time.setText(matchedUserModel.getTime());
                TextView pointsperkm = rideView.findViewById(R.id.pointsperkm);
                pointsperkm.setText(String.format("%s %s",matchedUserModel.getPrice(),"Points/Km"));

                start = new LatLng(matchedUserModel.getStartLatitude(), matchedUserModel.getStartLongitude());
                end = new LatLng(matchedUserModel.getEndLatitude(), matchedUserModel.getEndLongitude());
                float[] results = new float[1];
                Location.distanceBetween(start.latitude, start.longitude,
                        end.latitude, end.longitude, results);
                TextView route_distance = rideView.findViewById(R.id.route_distance);
                route_distance.setText(getReadableDistance(results[0]));

                TextView price = rideView.findViewById(R.id.price);
                price.setText(String.format("%s %s",String.valueOf((int)(Integer.parseInt(matchedUserModel.getPrice()) * (results[0]/1000))),"Points"));


                ImageView userImage = rideView.findViewById(R.id.userImage);
                Glide.with(getActivity())
                        .load(matchedUserModel.getImageUri())
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_default)
                        .into(userImage);


            }

            SupportMapFragment mapFragment = null;
            if (getFragmentManager() != null) {
                mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
            }
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
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
        if(googleMap != null){
            drawRoutePathOnMap(googleMap);
        }
    }

    private  void drawRoutePathOnMap(GoogleMap googleMap){
        List<LatLng> path = new ArrayList<>();
        path.add(start);
        path.add(end);
        PolylineOptions polyLine = new PolylineOptions();
        polyLine.addAll(path);
        polyLine.color(R.color.green);
        polyLine.width(6);
        polyLine.zIndex(1f);
        polyLine.geodesic(true);
        googleMap.addPolyline(polyLine);
        zoomRouthPath(googleMap, path,getActivity(),0);
    }

    private static String getReadableDistance(float distance) {
        int distanceInMts = (int) distance;
        StringBuilder distanceSB = new StringBuilder();
        if (distanceInMts > 1000) {
            distanceSB.append(new DecimalFormat("0.00").format(distance / 1000));
            distanceSB.append(" KM");
        } else {
            distanceSB.append(distanceInMts);
            distanceSB.append(" Mts");
        }
        return distanceSB.toString();
    }

    //Positioning the Map by zoom the exact start and end location
    private static void zoomRouthPath(final GoogleMap googleMap, List<LatLng> latLngData, FragmentActivity activity, double widthPercent) {
        try {
            final LatLngBounds.Builder bc = new LatLngBounds.Builder();
            if (latLngData != null && latLngData.size() > 0) {
                for (LatLng l : latLngData) {
                    bc.include(l);
                }
                final int width = activity.getResources().getDisplayMetrics().widthPixels;
                int padding = 100;
                if (widthPercent != 0) {
                    padding = (int) (width * widthPercent);
                }
                int w = width / 3;
                if (padding >= w) {
                    padding = (int) (width * 0.15);
                }
                Handler handler = new Handler();
                final int finalPadding = padding;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LatLngBounds bounds = bc.build();

                        try {
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, finalPadding);
                            googleMap.animateCamera(cu);
                        } catch (Throwable throwable) {
                            Log.e(TAG, "Animate failed", throwable);
                        }

                    }
                }, 200);
            }
        } catch (Throwable th) {
            Log.e(TAG, "zoomRouthPath failed", th);
        }
    }

}
