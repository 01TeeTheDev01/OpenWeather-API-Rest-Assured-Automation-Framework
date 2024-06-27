package com.openweather.api.repositories;

public interface IConfigFileReader {
    String getConfigFromFile(String pathToTxtFile);
}
