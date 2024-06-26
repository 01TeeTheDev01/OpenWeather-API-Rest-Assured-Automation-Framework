package com.openweather.api.helpers;

import com.openweather.api.services.IConfigFileReader;

import java.io.*;

public class ConfigFileReader implements IConfigFileReader {

    @Override
    public String getConfigFromFile(String pathToTxtFile) {

        if(pathToTxtFile.isBlank() || pathToTxtFile.isEmpty()){
            System.out.println("""
                    File path cannot be empty!
                    """);
            return "N/A";
        }

        String config = null;

        try{
            var reader = new BufferedReader(new FileReader(pathToTxtFile));

            while(reader.ready())
                config = reader.readLine();

            reader.close();

        }catch (Exception ex){
            System.out.printf("""
                    Oops... something went wrong: %s
                    """,
                    ex.getMessage());
        }

        return config;
    }
}
