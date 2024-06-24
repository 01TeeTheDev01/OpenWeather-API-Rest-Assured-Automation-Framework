package com.openweather.api.common;

import com.openweather.api.utils.OpenWeatherContentType;

import java.util.HashMap;
import java.util.Map;

public class OpenWeatherContent {

    private final Map<String, String> contentTypes = new HashMap<>();

    public OpenWeatherContent(){
        contentTypes.put("json","application/json");
        contentTypes.put("xml","application/xml");
    }

    public String getContentType(OpenWeatherContentType openWeatherContentType){
        switch (openWeatherContentType){
            case JSON -> {
                return String.valueOf(contentTypes.get("json"));
            }
            case XML -> {
                return String.valueOf(contentTypes.get("xml"));
            }
            default -> {
                return "Unable to determine content-type!";
            }
        }
    }
}
