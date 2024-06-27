package com.openweather.api.services;

import com.openweather.api.repositories.IOpenWeatherDbHelper;

import java.sql.Connection;
import java.sql.DriverManager;

public class OpenWeatherDbHelper implements IOpenWeatherDbHelper {

    private final String configString;
    private Connection connection;

    public OpenWeatherDbHelper(String configString) {
        this.configString = configString;
    }

    @Override
    public void setupConnection(){
        try{

            connection = DriverManager.getConnection(configString);

            if(connection == null ||
                    connection.isClosed()) {
                System.out.print("""
                         The connection to the server is currently non-operational.\s
                        \s
                         Unable to proceed with acquiring the API key.
                        \s""");
            }

        }catch (Exception ex){
            System.out.printf("""
                    Oops.... we've encountered an error: %s
                    """,
                    ex.getMessage());
        }
    }

    @Override
    public String getApiKey(int col){
        try{
            if(connection != null &&
                    !connection.isClosed() &&
                    col > 0){
                var results = connection
                        .createStatement()
                        .executeQuery("""
                    select *
                    from public."OpenWeather"
                    where "Id" = 1;
                    """);

                if(results == null ||
                        results.wasNull()){
                    System.out.println("Failed to retrieve key from data source!");

                    connection.close();

                    return "N/A";
                }

                String apiKey = null;

                while(results.next())
                    apiKey = results.getString(2);

                connection.close();

                return apiKey;
            }

            return "N/A";
        }catch (Exception ex){
            System.out.printf("""
                    Oops.... we've encountered an error: %s
                    """,
                    ex.getMessage());
            return "N/A";
        }
    }
}