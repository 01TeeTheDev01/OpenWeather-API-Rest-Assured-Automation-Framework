package com.openweather.api.helpers;

import com.openweather.api.services.IOpenWeatherDbReader;

import java.sql.Connection;
import java.sql.DriverManager;

public class OpenWeatherDbReader implements IOpenWeatherDbReader {

    private final String connString, userName, password;
    private Connection connection;

    public OpenWeatherDbReader(String connString, String userName, String password) {
        this.connString = connString;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void setupConnection(){
        try{
            connection = DriverManager.getConnection(connString, userName, password);

            if(connection.isClosed()) {
                System.out.print("""
                         The connection to the server is currently non-operational.\s
                        \s
                         Unable to proceed with acquiring the APi key.
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
            if(!connection.isClosed() && col > 0){
                var results = connection
                        .createStatement()
                        .executeQuery("""
                    select *
                    from public."OpenWeather"
                    where Id = 1;
                    """);

                if(results == null ||
                        results.wasNull() ||
                        results.getString(col).isEmpty() ||
                        results.getString(col).isBlank()){
                    System.out.println("Failed to retrieve key from data source!");

                    connection.close();

                    return "N/A";
                }

                connection.close();

                return results.getString(col);
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