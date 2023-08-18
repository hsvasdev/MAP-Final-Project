package com.example.mainactivity.api;

import android.util.Log;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;
    private static WeatherApi weatherApi = null;
    private static CitySuggestionsApi citySuggestionsApi = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static WeatherApi getWeatherApi() {
        if (weatherApi == null) {
            weatherApi = getClient().create(WeatherApi.class);
        }
        return weatherApi;
    }

    public static CitySuggestionsApi getCitySuggestionsApi() {
        if (citySuggestionsApi == null) {
            citySuggestionsApi = getClient().create(CitySuggestionsApi.class);
        }
        return citySuggestionsApi;
    }

    public interface CitySuggestionsApi {
        @GET("suggest")
        Call<List<String>> getCitySuggestions(@Query("q") String query, @Query("apiKey") String apiKey);
    }

    public static void fetchWeatherForCity(String city, Callback<WeatherResponse> callback) {
        String apiKey = "6c7e29d5e8d3fd5e2193ed5bf5bd822a";
        Call<WeatherResponse> call = getWeatherApi().getWeatherByCity(city, apiKey);
        call.enqueue(callback);
    }

    public static void fetchCitySuggestions(String query, Callback<List<String>> callback) {
        String apiKey = "6c7e29d5e8d3fd5e2193ed5bf5bd822a";
        Call<List<String>> call = getCitySuggestionsApi().getCitySuggestions(query, apiKey);
        call.enqueue(callback);
    }
}
