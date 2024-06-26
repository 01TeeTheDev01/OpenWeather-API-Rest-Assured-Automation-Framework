package com.openweather.api.models;

public class WeatherStation {

    private final String external_id, name;
    private final double latitude, longitude;
    private final int altitude;

    public WeatherStation(String external_id, String name, double latitude, double longitude, int altitude) {
        this.external_id = external_id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String getExternal_id() {
        return external_id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }
}