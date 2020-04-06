package com.example.georide;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRideActivity extends AppCompatActivity {

    public RecyclerView matchedUserRecyclerView;
    private String TAG = GetRideActivity.class.getSimpleName();
    private int REQUEST_PLACE_PICKER_START_LOCATION = 1;
    private int REQUEST_PLACE_PICKER_END_LOCATION = 2;
    private TextView tv_start_location, tv_end_location;
    private List<MatchedUserModel> matchedUserModelList;
    private MatchedUserAdapter matchedUserAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ride);

        tv_start_location = findViewById(R.id.tv_start_location);
        tv_end_location = findViewById(R.id.tv_end_location);
        matchedUserRecyclerView = findViewById(R.id.matcheduser_recyclerview);


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
                setAddressView(selectedPlace, true);
                showMatchedUsers();
            } else if (requestCode == REQUEST_PLACE_PICKER_END_LOCATION) {
                Place selectedPlace = PlacePicker.getPlace(this, data);
                setAddressView(selectedPlace, false);
                showMatchedUsers();
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

    private void showMatchedUsers() {
        if (!tv_start_location.getText().toString().isEmpty() && !tv_end_location.getText().toString().isEmpty()) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://my-json-server.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GetApi jsonPathHolderApi = retrofit.create(GetApi.class);

            progressDialog = new ProgressDialog(GetRideActivity.this);
            progressDialog.show();
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(new ProgressBar(GetRideActivity.this));
            Toast.makeText(this, "Please wait..Fetching Matched Users", Toast.LENGTH_SHORT).show();

            Call<List<MatchedUserModel>> response = jsonPathHolderApi.getMatchedUser();
            response.enqueue(new retrofit2.Callback<List<MatchedUserModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<MatchedUserModel>> call, @NonNull Response<List<MatchedUserModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        matchedUserModelList = response.body();
                        matchedUserRecyclerView.setVisibility(View.VISIBLE);
                        matchedUserAdapter = new MatchedUserAdapter(GetRideActivity.this, matchedUserModelList);
                        matchedUserRecyclerView.setLayoutManager(new LinearLayoutManager(GetRideActivity.this));
                        matchedUserRecyclerView.setAdapter(matchedUserAdapter);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<MatchedUserModel>> call, @NonNull Throwable t) {
                    Log.e(TAG, "RetrofitCall Failed", t);
                    progressDialog.dismiss();
                }
            });
        }
    }
}
