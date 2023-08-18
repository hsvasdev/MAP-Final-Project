package com.example.mainactivity.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mainactivity.R;
import com.example.mainactivity.api.RetrofitClient;
import com.example.mainactivity.api.WeatherResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

import android.widget.Toast;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import java.util.Set;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SearchView searchView;
    private ViewPager2 viewPager2;
    private WeatherPagerAdapter weatherPagerAdapter;

    private Button btnViewHistory;
    private Button btnViewFavorites;
    private Button btnAddFavorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Weather search functionality
        searchView = findViewById(R.id.searchCity);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchWeather(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewPager2 = findViewById(R.id.viewPager);
        weatherPagerAdapter = new WeatherPagerAdapter(new ArrayList<>());
        viewPager2.setAdapter(weatherPagerAdapter);

        // Button to view weather search history
        btnViewHistory = findViewById(R.id.btnViewHistory);
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity();
            }
        });

        btnViewFavorites = findViewById(R.id.btnViewFavorites);
        btnViewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        btnAddFavorite = findViewById(R.id.btnAddFavorite);
        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCityToFavorites();
            }
        });
    }

    private void HistoryActivity() {
        List<WeatherResponse> weatherHistory = weatherPagerAdapter.getWeatherHistory();
        if (weatherHistory.isEmpty()) {
            Toast.makeText(this, "No search history available", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> historyStrings = new ArrayList<>();
        for (WeatherResponse response : weatherHistory) {
            historyStrings.add("City: " + response.getName() + ", Temperature: " + response.getMain().getTemp() + "°F");
        }

        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putStringArrayListExtra("history", historyStrings);
        startActivity(intent);
    }

    private String getCurrentCity() {
        int currentPosition = viewPager2.getCurrentItem();

        WeatherResponse currentWeatherResponse = weatherPagerAdapter.getCurrentWeatherResponse(currentPosition);

        if (currentWeatherResponse != null) {
            return currentWeatherResponse.getName();
        }

        return null;
    }

    private void addCityToFavorites() {
        String city = getCurrentCity();
        if (city != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
            Set<String> favoritesSet = sharedPreferences.getStringSet("favoritesList", new HashSet<>());

            favoritesSet.add(city);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("favoritesList", favoritesSet);
            editor.apply();

            Toast.makeText(this, "City added to favorites!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No city to add to favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchWeather (String city){
            RetrofitClient.fetchWeatherForCity(city, new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful()) {
                        WeatherResponse weatherResponse = response.body();
                        if (weatherResponse != null) {
                            weatherPagerAdapter.addWeatherData(weatherResponse);
                            viewPager2.setCurrentItem(weatherPagerAdapter.getItemCount() - 1, true);
                        } else {
                            Log.e(TAG, "WeatherResponse is null.");
                        }
                    } else {
                        Log.e(TAG, "API call failed. Response code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    Log.e(TAG, "API call failed: " + t.getMessage());
                }
            });
        }
    }

