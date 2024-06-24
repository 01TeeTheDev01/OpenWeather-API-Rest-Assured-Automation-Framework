package com.openweather.api.models;

public class Weather {

    private final String externalId, name;
    private final double latitude, longitude;
    private final int altitude;

    public Weather(String externalId, String name,
                   double latitude, double longitude,
                   int altitude) {
        this.externalId = externalId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String getExternalId() {
        return externalId;
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