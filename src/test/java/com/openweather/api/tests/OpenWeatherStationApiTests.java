package com.openweather.api.tests;

import com.openweather.api.builder.OpenWeatherRequestBuilder;
import com.openweather.api.builder.WeatherStationBuilder;
import com.openweather.api.common.OpenWeatherQueryParam;
import com.openweather.api.common.OpenWeatherStatusCode;
import com.openweather.api.helpers.OpenWeatherDbReader;
import com.openweather.api.helpers.OpenWeatherProperty;
import com.openweather.api.services.IOpenWeatherDbReader;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.notNullValue;

@Test
@Feature("OpenWeather API")
public class OpenWeatherStationApiTests {

    private final IOpenWeatherDbReader reader;

    public OpenWeatherStationApiTests(){
        reader = new OpenWeatherDbReader("","","");
    }

    @Story("AS AN API USER, CREATE A NEW WEATHER STATION.")
    @Description("CREATE A NEW WEATHER STATION.")
    public void POSITIVE_createStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        if(apiKey.equals("N/A"))
            return;

        //Build station
        var weatherBuilder =
                new WeatherStationBuilder();

        var weatherStation =
                weatherBuilder
                        .withExternalId("SF_TEST099")
                        .withName("San Francisco Test Station")
                        .withLatitude(-91)
                        .withLongitude(-200)
                        .withAltitude(22)
                        .build();

        //Send POST
        var response =
                OpenWeatherRequestBuilder
                        .createStation(new OpenWeatherQueryParam(apiKey), weatherStation);

        //Check response
        if(response == null)
            return;

        //Validate response
        response
                .then()
                .assertThat()
                .statusCode(OpenWeatherStatusCode.CREATED.getCode());
    }

    @Test(dependsOnMethods = "POSITIVE_createStation")
    @Story("AS AN API USER, GET NEW WEATHER STATION.")
    @Description("AS AN API USER, GET NEW WEATHER STATION.")
    public void POSITIVE_getStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        if(apiKey.equals("N/A"))
            return;

        var params = new OpenWeatherQueryParam[]{
                new OpenWeatherQueryParam(apiKey),
                new OpenWeatherQueryParam(OpenWeatherProperty.stationId)
        };

        //Send GET
        var response =
                OpenWeatherRequestBuilder
                        .getStation(params);

        //Check response
        if(response == null)
            return;

        //Validate response
        response
                .then()
                .assertThat()
                .statusCode(OpenWeatherStatusCode.OK.getCode());
    }

    @Test(dependsOnMethods = "POSITIVE_getStation")
    @Story("AS AN API USER, DELETE WEATHER STATION.")
    @Description("AS AN API USER, DELETE WEATHER STATION.")
    public void POSITIVE_deleteStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        if(apiKey.equals("N/A"))
            return;

        var params = new OpenWeatherQueryParam[]{
                new OpenWeatherQueryParam(apiKey),
                new OpenWeatherQueryParam(OpenWeatherProperty.stationId)
        };

        //Send DELETE
        var response =
                OpenWeatherRequestBuilder
                        .deleteStation(params);

        //Check response
        if(response == null)
            return;

        //Validate response
        response
                .then()
                .assertThat()
                .statusCode(OpenWeatherStatusCode.NO_CONTENT.getCode());
    }
}