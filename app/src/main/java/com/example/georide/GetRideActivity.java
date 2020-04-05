package com.example.georide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class GetRideActivity extends AppCompatActivity {

    private String TAG = GetRideActivity.class.getSimpleName();
    private int REQUEST_PLACE_PICKER_START_LOCATION = 1;
    private int REQUEST_PLACE_PICKER_END_LOCATION = 2;
    private TextView tv_start_location, tv_end_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ride);

        tv_start_location = findViewById(R.id.tv_start_location);
        tv_end_location = findViewById(R.id.tv_end_location);

        tv_start_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(GetRideActivity.this), REQUEST_PLACE_PICKER_START_LOCATION);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "GooglePlayServicesRepairableException", e);
                }
            }
        });

        tv_end_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(GetRideActivity.this), REQUEST_PLACE_PICKER_END_LOCATION);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "GooglePlayServicesRepairableException", e);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PLACE_PICKER_START_LOCATION) {
                Place selectedPlace = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", selectedPlace.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                setAddressView(selectedPlace, true);
            } else if (requestCode == REQUEST_PLACE_PICKER_END_LOCATION) {
                Place selectedPlace = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", selectedPlace.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                setAddressView(selectedPlace, false);
            }
        }
    }

    private void setAddressView(Place curPlace, boolean startLocation) {
        if (curPlace != null) {
            if (startLocation)
                tv_start_location.setText(curPlace.getAddress());
            else
                tv_end_location.setText(curPlace.getAddress());

        }
    }
}
