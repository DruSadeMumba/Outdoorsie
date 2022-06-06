package com.drusade.outdoorsie.network;

import com.drusade.outdoorsie.models.YahooWeatherLocationSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YahooWeatherApi {
    @GET ("/weather")
    Call<YahooWeatherLocationSearchResponse> getWeather(
            @Query("location") String location,
            @Query("u") String u
    );
}
