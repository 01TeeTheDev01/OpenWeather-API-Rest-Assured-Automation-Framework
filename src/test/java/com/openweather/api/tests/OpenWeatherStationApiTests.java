package com.openweather.api.tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.openweather.api.builders.OpenWeatherRequestBuilder;
import com.openweather.api.builders.WeatherStationBuilder;
import com.openweather.api.common.OpenWeatherQueryParam;
import com.openweather.api.common.OpenWeatherStatusCode;
import com.openweather.api.services.ConfigFileReader;
import com.openweather.api.services.OpenWeatherDbHelper;
import com.openweather.api.util.OpenWeatherProperty;
import com.openweather.api.repositories.IConfigFileReader;
import com.openweather.api.repositories.IOpenWeatherDbHelper;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.HashMap;


@Test
@Feature("OpenWeather API")
public class OpenWeatherStationApiTests {

    private final IOpenWeatherDbHelper reader;
    private final Faker faker;
    private final HashMap<String, OpenWeatherQueryParam> params;

    public OpenWeatherStationApiTests(){
        params = new HashMap<>();
        faker = new Faker();
        IConfigFileReader configFileReader = new ConfigFileReader();
        reader = new OpenWeatherDbHelper(
                configFileReader.getConfigFromFile("C:\\Temp\\OpenWeatherConfig.txt")
        );
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

        //Add key to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));

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
                        .createStation(params, weatherStation);

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

        //Add key to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));

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
                        .createStation(params, weatherStation);

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

        //Add keys to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));
        params.put("id", new OpenWeatherQueryParam(faker.idNumber().valid()));

        //Send GET
        var response =
                OpenWeatherRequestBuilder
                        .getStation(params);

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

        //Add keys to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));
        params.put("id", new OpenWeatherQueryParam(String.valueOf(faker.number().randomNumber())));
        params.put("empty_data", new OpenWeatherQueryParam(new Gson().toJson(null)));

        OpenWeatherProperty
                .weatherStation
                .setName(String.format(
                        "%s TEST STATION",
                        faker.company().name().toUpperCase())
                );

        //Send PUT
        var response =
                OpenWeatherRequestBuilder
                        .updateStation(params);

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

        //Add keys to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));
        params.put("id", new OpenWeatherQueryParam(String.valueOf(faker.number().randomNumber())));

        //Send DELETE
        var response =
                OpenWeatherRequestBuilder
                        .deleteStation(params);

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

        params.put("apiKey", new OpenWeatherQueryParam(apiKey));

        //Build station
        var weatherBuilder =
                new WeatherStationBuilder();

        int minLat = -90, maxLat = 90, minLong = -180, maxLong = 180;

        var weatherStation =
                weatherBuilder
                        .withExternalId("SF_TEST099")
                        .withName("San Francisco Test Station")
                        .withLatitude(faker.number().randomDouble(2, minLat, maxLat))
                        .withLongitude(faker.number().randomDouble(2, minLong, maxLong))
                        .withAltitude(22)
                        .build();

        //Send POST
        var response =
                OpenWeatherRequestBuilder
                        .createStation(params, weatherStation);

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

        //Add keys to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));
        params.put("id", new OpenWeatherQueryParam(OpenWeatherProperty.stationId));

        //Send GET
        var response =
                OpenWeatherRequestBuilder
                        .getStation(params);

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

        //Add keys to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));
        params.put("id", new OpenWeatherQueryParam(OpenWeatherProperty.stationId));
        params.put("empty_data", new OpenWeatherQueryParam(new Gson().toJson(OpenWeatherProperty.weatherStation)));

        //Send PUT
        var response =
                OpenWeatherRequestBuilder
                        .updateStation(params);

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

        //Add keys to dictionary
        params.put("apiKey", new OpenWeatherQueryParam(apiKey));
        params.put("id", new OpenWeatherQueryParam(OpenWeatherProperty.stationId));

        //Send DELETE
        var response =
                OpenWeatherRequestBuilder
                        .deleteStation(params);

        //Validate response
        response
                .then()
                .assertThat()
                .statusCode(OpenWeatherStatusCode.NO_CONTENT.getCode());
    }
}