package com.openweather.api.builder;

import com.openweather.api.common.OpenWeatherBaseEndpoint;
import com.openweather.api.common.OpenWeatherBasePath;
import com.openweather.api.common.OpenWeatherQueryParam;
import com.openweather.api.helpers.OpenWeatherProperty;
import com.openweather.api.models.WeatherStation;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OpenWeatherRequestBuilder {

    public static Response createStation(OpenWeatherQueryParam openWeatherQueryParam, WeatherStation weatherStation){

        if(weatherStation == null ||
                openWeatherQueryParam == null ||
                openWeatherQueryParam.getQueryParam().isEmpty() ||
                openWeatherQueryParam.getQueryParam().isBlank())
            return null;

        String url =
                String.format("%s%s",
                                OpenWeatherBasePath.baseUrl,
                                OpenWeatherBaseEndpoint.ENDPOINT);

        var response =
                given()
                        .accept(ContentType.JSON)
                .queryParam("appid", openWeatherQueryParam.getQueryParam())
                .contentType(ContentType.JSON)
                .body(weatherStation)
                .post(url)
                .then()
                .extract()
                .response();

        OpenWeatherProperty.stationId = response.getBody().jsonPath().getString("ID");

        return response;
    }

    public static Response getStation(OpenWeatherQueryParam[] openWeatherQueryParam){

        if(openWeatherQueryParam == null ||
                openWeatherQueryParam[0].getQueryParam().isEmpty() ||
                openWeatherQueryParam[0].getQueryParam().isBlank() ||
                openWeatherQueryParam[1].getQueryParam().isEmpty() ||
                openWeatherQueryParam[1].getQueryParam().isBlank())
            return null;

        String url =
                String.format("%s%s/%s",
                        OpenWeatherBasePath.baseUrl,
                        OpenWeatherBaseEndpoint.ENDPOINT,
                        openWeatherQueryParam[1].getQueryParam());

        return given()
                .queryParam("appid", openWeatherQueryParam[0].getQueryParam())
                .contentType(ContentType.JSON)
                .get(url)
                .then()
                .extract()
                .response();
    }

    public static Response deleteStation(OpenWeatherQueryParam[] openWeatherQueryParam){

        if(openWeatherQueryParam == null ||
                openWeatherQueryParam[0].getQueryParam().isEmpty() ||
                openWeatherQueryParam[0].getQueryParam().isBlank() ||
                openWeatherQueryParam[1].getQueryParam().isEmpty() ||
                openWeatherQueryParam[1].getQueryParam().isBlank())
            return null;

        String url =
                String .format("%s%s/%s",
                        OpenWeatherBasePath.baseUrl,
                        OpenWeatherBaseEndpoint.ENDPOINT,
                        openWeatherQueryParam[1].getQueryParam());

        return given()
                .queryParam("appid", openWeatherQueryParam[0].getQueryParam())
                .contentType(ContentType.JSON)
                .delete(url)
                .then()
                .extract()
                .response();
    }
}