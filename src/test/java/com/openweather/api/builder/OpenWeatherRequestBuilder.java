package com.openweather.api.builder;

import com.google.gson.Gson;
import com.openweather.api.common.OpenWeatherBaseEndpoint;
import com.openweather.api.common.OpenWeatherBasePath;
import com.openweather.api.common.OpenWeatherQueryParam;
import com.openweather.api.helpers.OpenWeatherProperty;
import com.openweather.api.models.WeatherStation;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class OpenWeatherRequestBuilder {

    public static Response createStation(HashMap<String, OpenWeatherQueryParam> openWeatherQueryParam, WeatherStation weatherStation){

        if(weatherStation == null ||
                openWeatherQueryParam == null ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isBlank())
            return null;

        String url =
                String.format("%s%s",
                                OpenWeatherBasePath.baseUrl,
                                OpenWeatherBaseEndpoint.ENDPOINT
                );

        var response =
                given()
                        .accept(ContentType.JSON)
                .queryParam("appid", openWeatherQueryParam.get("apiKey").getQueryParam())
                .contentType(ContentType.JSON)
                .body(weatherStation)
                .post(url)
                .then()
                .extract()
                .response();

        OpenWeatherProperty.stationId =
                response
                        .getBody()
                        .jsonPath()
                        .getString("ID");

        return response;
    }

    public static Response getStation(HashMap<String, OpenWeatherQueryParam> openWeatherQueryParam){

        if(openWeatherQueryParam == null ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isBlank() ||
                openWeatherQueryParam.get("id").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("id").getQueryParam().isBlank())
            return null;

        String url =
                String.format("%s%s/%s",
                        OpenWeatherBasePath.baseUrl,
                        OpenWeatherBaseEndpoint.ENDPOINT,
                        openWeatherQueryParam.get("id").getQueryParam()
                );

        var response =
                given()
                .queryParam("appid", openWeatherQueryParam.get("apiKey").getQueryParam())
                .contentType(ContentType.JSON)
                .get(url)
                .then()
                .extract()
                .response();

        OpenWeatherProperty.weatherStation =
                new Gson()
                        .fromJson(
                                response.getBody().asPrettyString(),
                                WeatherStation.class
                        );

        return response;
    }

    public static Response updateStation(HashMap<String, OpenWeatherQueryParam> openWeatherQueryParam){

        if(openWeatherQueryParam == null ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isBlank() ||
                openWeatherQueryParam.get("id").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("id").getQueryParam().isBlank() ||
                openWeatherQueryParam.get("empty_data").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("empty_data").getQueryParam().isBlank())
            return null;

        String url =
                String.format("%s%s/%s",
                        OpenWeatherBasePath.baseUrl,
                        OpenWeatherBaseEndpoint.ENDPOINT,
                        openWeatherQueryParam.get("id").getQueryParam()
                );

        var response =
                given()
                        .queryParam("appid", openWeatherQueryParam.get("apiKey").getQueryParam())
                        .contentType(ContentType.JSON)
                        .body(openWeatherQueryParam.get("empty_data").getQueryParam())
                        .put(url)
                        .then()
                        .extract()
                        .response();

        OpenWeatherProperty.weatherStation =
                new Gson()
                        .fromJson(
                                response.getBody().asPrettyString(),
                                WeatherStation.class
                        );

        return response;
    }

    public static Response deleteStation(HashMap<String, OpenWeatherQueryParam> openWeatherQueryParam){

        if(openWeatherQueryParam == null ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("apiKey").getQueryParam().isBlank() ||
                openWeatherQueryParam.get("id").getQueryParam().isEmpty() ||
                openWeatherQueryParam.get("id").getQueryParam().isBlank())
            return null;

        String url =
                String.format("%s%s/%s",
                        OpenWeatherBasePath.baseUrl,
                        OpenWeatherBaseEndpoint.ENDPOINT,
                        openWeatherQueryParam.get("id").getQueryParam()
                );

        return given()
                .queryParam("appid", openWeatherQueryParam.get("apiKey").getQueryParam())
                .contentType(ContentType.JSON)
                .delete(url)
                .then()
                .extract()
                .response();
    }
}