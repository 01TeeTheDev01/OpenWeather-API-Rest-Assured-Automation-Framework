package com.openweather.api.tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.openweather.api.builder.OpenWeatherRequestBuilder;
import com.openweather.api.builder.WeatherStationBuilder;
import com.openweather.api.common.OpenWeatherQueryParam;
import com.openweather.api.common.OpenWeatherStatusCode;
import com.openweather.api.helpers.ConfigFileReader;
import com.openweather.api.helpers.OpenWeatherDbReader;
import com.openweather.api.helpers.OpenWeatherProperty;
import com.openweather.api.services.IConfigFileReader;
import com.openweather.api.services.IOpenWeatherDbReader;
import io.qameta.allure.*;
import org.testng.annotations.Test;


@Test
@Feature("OpenWeather API")
public class OpenWeatherStationApiTests {

    private final IOpenWeatherDbReader reader;
    private final Faker faker;

    public OpenWeatherStationApiTests(){
        IConfigFileReader configFileReader = new ConfigFileReader();

        reader = new OpenWeatherDbReader(
                configFileReader.getConfigFromFile("C:\\Temp\\OpenWeatherConfig.txt")
        );

        faker = new Faker();
    }

    @Story("AS AN API USER, I CREATE A NEW WEATHER STATION WITH INCORRECT API KEY.")
    @Description("CREATE A NEW WEATHER STATION WITH INCORRECT API KEY. EXPECTED RESULT IS 401.")
    @Severity(SeverityLevel.BLOCKER)
    public void NEGATIVE_createStationWithIncorrectApiKey(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = String.format("%sg",
                reader.getApiKey(1)
        );

        //Build station
        var weatherBuilder =
                new WeatherStationBuilder();

        var weatherStation =
                weatherBuilder
                        .withExternalId("SF_TEST099")
                        .withName("San Francisco Test Station")
                        .withLatitude(faker.number().numberBetween(190, 240))
                        .withLongitude(faker.number().numberBetween(190, 240))
                        .withAltitude(faker.number().numberBetween(1, 150))
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
                .statusCode(OpenWeatherStatusCode.UNAUTHORIZED.getCode());
    }

    @Story("AS AN API USER, I CREATE A NEW WEATHER STATION WITH OUT OF RANGE VALUES.")
    @Description("CREATE A NEW WEATHER STATION WITH OUT OF RANGE VALUES. EXPECTED RESULT IS 400.")
    @Severity(SeverityLevel.MINOR)
    public void NEGATIVE_createStationWithOutOfRangeValues(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        //Build station
        var weatherBuilder =
                new WeatherStationBuilder();

        var weatherStation =
                weatherBuilder
                        .withExternalId("SF_TEST099")
                        .withName("San Francisco Test Station")
                        .withLatitude(faker.number().numberBetween(190, 240))
                        .withLongitude(faker.number().numberBetween(190, 240))
                        .withAltitude(faker.number().numberBetween(1, 150))
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
                .statusCode(OpenWeatherStatusCode.BAD_REQUEST.getCode());
    }

    @Test(dependsOnMethods = "NEGATIVE_createStationWithOutOfRangeValues")
    @Story("AS AN API USER, I GET A WEATHER STATION FROM INVALID ID.")
    @Severity(SeverityLevel.MINOR)
    @Description("GET WEATHER STATION FROM INVALID ID. EXPECTED RESULT IS 400.")
    public void NEGATIVE_getStationWithInvalidId(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);
        
        var params = new OpenWeatherQueryParam[]{
                new OpenWeatherQueryParam(apiKey),
                new OpenWeatherQueryParam(faker.idNumber().valid())
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
                .statusCode(OpenWeatherStatusCode.BAD_REQUEST.getCode());
    }

    @Test(dependsOnMethods = "NEGATIVE_getStationWithInvalidId")
    @Story("AS AN API USER, I UPDATE WEATHER STATION WITH EMPTY OBJECT.")
    @Description("UPDATE WEATHER STATION WITH EMPTY OBJECT. EXPECTED RESULT IS 400.")
    @Severity(SeverityLevel.MINOR)
    public void NEGATIVE_updateStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        OpenWeatherProperty
                .weatherStation
                .setName(String.format(
                        "%s TEST STATION",
                        faker.company().name().toUpperCase())
                );

        var params = new OpenWeatherQueryParam[]{
                new OpenWeatherQueryParam(apiKey),
                new OpenWeatherQueryParam(String.valueOf(faker.number().randomNumber())),
                new OpenWeatherQueryParam(new Gson().toJson(null))
        };

        //Send PUT
        var response =
                OpenWeatherRequestBuilder
                        .updateStation(params);

        //Check response
        if(response == null)
            return;

        //Validate response
        response
                .then()
                .assertThat()
                .statusCode(OpenWeatherStatusCode.BAD_REQUEST.getCode());
    }

    @Test(dependsOnMethods = "NEGATIVE_updateStation")
    @Story("AS AN API USER, I DELETE WEATHER STATION WITH INVALID ID.")
    @Description("DELETE WEATHER STATION WITH INVALID ID. EXPECTED RESULT IS 400.")
    @Severity(SeverityLevel.MINOR)
    public void NEGATIVE_deleteStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        var params = new OpenWeatherQueryParam[]{
                new OpenWeatherQueryParam(apiKey),
                new OpenWeatherQueryParam(String.valueOf(faker.number().randomNumber()))
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
                .statusCode(OpenWeatherStatusCode.BAD_REQUEST.getCode());
    }

    @Test(dependsOnMethods = "NEGATIVE_deleteStation")
    @Story("AS AN API USER, I CREATE A NEW WEATHER STATION.")
    @Description("CREATE A NEW WEATHER STATION. EXPECTED RESULT IS 201.")
    @Severity(SeverityLevel.NORMAL)
    public void POSITIVE_createStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        //Build station
        var weatherBuilder =
                new WeatherStationBuilder();

        var weatherStation =
                weatherBuilder
                        .withExternalId("SF_TEST099")
                        .withName("San Francisco Test Station")
                        .withLatitude(50.51)
                        .withLongitude(99.15)
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
    @Story("AS AN API USER, I GET A NEW WEATHER STATION.")
    @Description("GET WEATHER STATION. EXPECTED RESULT IS 200.")
    @Severity(SeverityLevel.NORMAL)
    public void POSITIVE_getStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

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
    @Story("AS AN API USER, I UPDATE THE NEWLY CREATED WEATHER STATION.")
    @Description("UPDATE WEATHER STATION. EXPECTED RESULT IS 200.")
    @Severity(SeverityLevel.NORMAL)
    public void POSITIVE_updateStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

        OpenWeatherProperty
                .weatherStation
                .setName(String.format(
                        "%s TEST STATION",
                        faker.company().name().toUpperCase())
                );

        var params = new OpenWeatherQueryParam[]{
                new OpenWeatherQueryParam(apiKey),
                new OpenWeatherQueryParam(OpenWeatherProperty.stationId),
                new OpenWeatherQueryParam(new Gson().toJson(OpenWeatherProperty.weatherStation))
        };

        //Send PUT
        var response =
                OpenWeatherRequestBuilder
                        .updateStation(params);

        //Check response
        if(response == null)
            return;

        //Validate response
        response
                .then()
                .assertThat()
                .statusCode(OpenWeatherStatusCode.OK.getCode());
    }

    @Test(dependsOnMethods = "POSITIVE_updateStation")
    @Story("AS AN API USER, I DELETE WEATHER STATION.")
    @Description("DELETE WEATHER STATION. EXPECTED RESULT IS 204.")
    @Severity(SeverityLevel.NORMAL)
    public void POSITIVE_deleteStation(){
        //Init database
        reader.setupConnection();

        //Get Api key
        var apiKey = reader
                .getApiKey(1);

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