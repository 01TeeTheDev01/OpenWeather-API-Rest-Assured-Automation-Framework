package com.openweather.api.builder;

import com.openweather.api.models.WeatherStation;

public class WeatherStationBuilder {

    private final String externalId, name;
    private final double latitude, longitude;
    private final int altitude;

    public WeatherStationBuilder(){
        externalId = null;
        name = null;
        latitude = 0;
        longitude = 0;
        altitude = 0;
    }

    public WeatherStationBuilder(String externalId, String name, double latitude, double longitude, int altitude) {
        this.externalId = externalId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public WeatherStationBuilder withExternalId(String externalId){
        return new WeatherStationBuilder(externalId, name, latitude, longitude, altitude);
    }

    public WeatherStationBuilder withName(String name){
        return new WeatherStationBuilder(externalId, name, latitude, longitude, altitude);
    }

    public WeatherStationBuilder withLatitude(double latitude){
        if(latitude < -90)
            return new WeatherStationBuilder(externalId, name, -90, longitude, altitude);

        if(latitude > 90)
            return new WeatherStationBuilder(externalId, name, 90, longitude, altitude);

        return new WeatherStationBuilder(externalId, name, latitude, longitude, altitude);
    }

    public WeatherStationBuilder withLongitude(double longitude){
        if(longitude < -180.0)
            return new WeatherStationBuilder(externalId, name, latitude, -180, altitude);

        if(longitude > 180.0)
            return new WeatherStationBuilder(externalId, name, latitude, 180, altitude);

        return new WeatherStationBuilder(externalId, name, latitude, longitude, altitude);
    }

    public WeatherStationBuilder withAltitude(int altitude){
        return new WeatherStationBuilder(externalId, name, latitude, longitude, altitude);
    }

    public WeatherStation build(){
        return new WeatherStation(externalId, name, latitude, longitude, altitude);
    }
}
