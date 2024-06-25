package com.openweather.api.services;

public interface IOpenWeatherDbReader {
    void setupConnection();
    String getApiKey(int col);
}
