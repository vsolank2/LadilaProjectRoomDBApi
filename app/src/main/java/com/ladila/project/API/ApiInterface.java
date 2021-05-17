package com.ladila.project.API;

import com.ladila.project.Pojo.ClsApiData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("3/movie/top_rated?")
    Call<ClsApiData> getAPIData(@Query("api_key") String api_key);

}
