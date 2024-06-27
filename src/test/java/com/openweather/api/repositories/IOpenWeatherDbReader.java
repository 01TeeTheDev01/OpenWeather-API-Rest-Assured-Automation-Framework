package com.openweather.api.repositories;

public interface IOpenWeatherDbReader {
    void setupConnection();
    String getApiKey(int col);
}
