package com.openweather.api.models;

public class WeatherStationInfo {

    private final String id, created_at, updated_at;
    private final int rank;
    private final WeatherStation weatherStation;

    public WeatherStationInfo(String id, String createdAt, String updatedAt, int rank, WeatherStation weatherStation) {
        this.id = id;
        created_at = createdAt;
        updated_at = updatedAt;
        this.rank = rank;
        this.weatherStation = weatherStation;
    }

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getRank() {
        return rank;
    }

    public WeatherStation getWeatherStation() {
        return weatherStation;
    }
}