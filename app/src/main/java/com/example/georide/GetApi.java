package com.example.georide;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetApi {

    @GET("AdithyaMpn/FakeJson/posts")
    Call<List<MatchedUserModel>> getMatchedUser();

    @GET
    Call<JsonObject> getJsonFromUrl(@Url String url, @Query("key")String key);
}
