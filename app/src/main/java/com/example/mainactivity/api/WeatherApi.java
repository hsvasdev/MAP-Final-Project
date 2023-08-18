package com.example.mainactivity.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Call<WeatherResponse> getWeatherByCity(@Query("q") String city, @Query("appid") String apiKey);

    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeatherByCoordinates(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String apiKey);
}
