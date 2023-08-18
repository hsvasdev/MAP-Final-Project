package com.example.mainactivity.api;

public class WeatherResponse {

    private Main main;
    private Weather[] weather;
    private Wind wind;
    private String name;

    public Main getMain() {
        return main;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }

    public static class Main {
        private double temp;
        private double humidity;

        public double getTemp() {
            return temp;
        }

        public double getHumidity() {
            return humidity;
        }
    }

    public static class Weather {
        private String description;
        private String icon;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class Wind {
        private double speed;

        public double getSpeed() {
            return speed;
        }
    }
}
