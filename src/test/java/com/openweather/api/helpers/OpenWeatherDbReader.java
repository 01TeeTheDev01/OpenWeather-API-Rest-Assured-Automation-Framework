package com.openweather.api.helpers;

import java.sql.Connection;
import java.sql.DriverManager;

public class OpenWeatherDbReader {

    private final String connString, userName, password;
    private Connection connection;

    public OpenWeatherDbReader(String connString, String userName, String password) {
        this.connString = connString;
        this.userName = userName;
        this.password = password;
    }

    public void setupConnection(){
        try{
            connection = DriverManager.getConnection(connString, userName, password);

            if(connection.isClosed()) {
                System.out.println("""
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

    public String getApiKey(int col){
        try{
            if(!connection.isClosed()){
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
                    return "N/A";
                }

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