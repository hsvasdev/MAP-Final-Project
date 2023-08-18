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
import android.content.SharedPreferences;
import java.util.Set;
import java.util.HashSet;


public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ListView listView = findViewById(R.id.listViewFavorites);

        // Get favorites from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);
        Set<String> favoritesSet = sharedPreferences.getStringSet("favoritesList", new HashSet<>());
        ArrayList<String> favoritesList = new ArrayList<>(favoritesSet);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritesList);
        listView.setAdapter(adapter);

        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
