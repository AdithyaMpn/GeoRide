package com.example.georide;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetApi {

    @GET("AdithyaMpn/FakeJson/posts")
    Call<List<MatchedUserModel>> getMatchedUser();
}
