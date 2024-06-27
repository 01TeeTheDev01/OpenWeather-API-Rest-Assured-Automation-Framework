package com.openweather.api.repositories;

public interface IOpenWeatherDbHelper {
    void setupConnection();
    String getApiKey(int col);
}
