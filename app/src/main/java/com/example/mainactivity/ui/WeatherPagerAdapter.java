package com.example.mainactivity.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.R;
import com.example.mainactivity.api.WeatherResponse;
import java.util.List;

public class WeatherPagerAdapter extends RecyclerView.Adapter<WeatherPagerAdapter.WeatherViewHolder> {

    private List<WeatherResponse> weatherData;

    public WeatherPagerAdapter(List<WeatherResponse> weatherData) {
        this.weatherData = weatherData;
    }

    public List<WeatherResponse> getWeatherHistory() {
        return weatherData;
    }

    public WeatherResponse getCurrentWeatherResponse(int position) {
        if (position >= 0 && position < weatherData.size()) {
            return weatherData.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_weather, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherResponse currentData = weatherData.get(position);
        holder.tvCityName.setText("City: " + currentData.getName());
        holder.tvTemperature.setText("Temperature: " + currentData.getMain().getTemp() + "°F");
        holder.tvHumidity.setText("Humidity: " + currentData.getMain().getHumidity() + "%");
        if (currentData.getWeather().length > 0) {
            holder.tvWeatherDescription.setText("Description: " + currentData.getWeather()[0].getDescription());
        }
        holder.tvWindSpeed.setText("Wind Speed: " + currentData.getWind().getSpeed() + " m/s");
    }

    @Override
    public int getItemCount() {
        return weatherData.size();
    }

    public void addWeatherData(WeatherResponse newWeatherResponse) {
        this.weatherData.add(newWeatherResponse);
        notifyDataSetChanged();
    }

    public List<WeatherResponse> getWeatherData() {
        return weatherData;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvCityName;
        TextView tvTemperature;
        TextView tvHumidity;
        TextView tvWeatherDescription;
        TextView tvWindSpeed;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvWeatherDescription = itemView.findViewById(R.id.tvWeatherDescription);
            tvWindSpeed = itemView.findViewById(R.id.tvWindSpeed);
        }
    }
}
