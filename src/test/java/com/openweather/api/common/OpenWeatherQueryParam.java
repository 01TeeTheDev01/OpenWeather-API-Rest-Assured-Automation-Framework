package com.openweather.api.common;

public class OpenWeatherQueryParam {

    private final String queryParam;

    public OpenWeatherQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getQueryParam() {
        return queryParam;
    }
}