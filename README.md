# OpenWeather API Rest-Assured Automation Framework

## Description
This project aims to develop a testing framework for the OpenWeatherMap API (http://api.openweathermap.org). The framework will automate testing of various HTTP methods such as POST, GET, PUT, and DELETE for different endpoints provided by the API.

## Table of Contents
1. [Introduction](#weather-api-testing-framework)
2. [Setup](#setup)
3. [Endpoints Tested](#endpoints-tested)
4. [Tools used](#tools-used)
5. [Usage](#usage)
6. [Pipeline Setup](#pipeline-setup)

## Setup
To set up the testing framework, ensure you have Java and Maven installed on your system. Clone the repository and configure the necessary dependencies in your `pom.xml` file.

```xml
<dependencies>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.2</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>com.googlecode.json-simple</groupId>
        <artifactId>json-simple</artifactId>
        <version>1.1.1</version>
    </dependency>

    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-testng</artifactId>
        <version>2.27.0</version>
    </dependency>

    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.11.0</version>
    </dependency>

    <dependency>
        <groupId>com.github.javafaker</groupId>
        <artifactId>javafaker</artifactId>
        <version>1.0.2</version>
    </dependency>
</dependencies>
```

## Endpoints Tested
The framework will cover testing of the following HTTP methods on various API endpoints:

1. POST
2. GET
3. PUT
4. DELETE

## Tools Used

- Java: Programming language for writing the test automation scripts.

### Maven
Build tool and dependency management for the project.

### TestNG
Testing framework for unit and integration tests in Java.

### Rest-Assured
Rest-Assured is a Java library that provides a fluent interface for testing and validating RESTful APIs. It allows you to easily make HTTP requests, validate responses, and perform assertions.

### JSON Simple
JSON Simple is a lightweight Java library for parsing and generating JSON (JavaScript Object Notation) data. It provides simple APIs for reading and writing JSON data, making it easy to work with JSON in Java applications.

### Allure-TestNG
Allure TestNG is an adapter for integrating Allure with the TestNG testing framework. Allure is a flexible framework designed to create detailed and comprehensive reports for test automation. It enhances test result reporting with rich visualization and analytics capabilities.

### GSON: 
Gson is a Java library that can be used to convert Java Objects into their JSON representation and vice versa. It provides methods to serialize Java objects to JSON and deserialize JSON back into Java objects.

### JavaFaker
JavaFaker is a library that generates fake data, such as names, addresses, and other types of data, which can be useful for testing and seeding databases with realistic test data. 

### GitHub Actions
CI/CD pipeline for automating builds, tests, and deployments.

## Usage
Run the below command in the project source folder to run all tests:
```shell
mvn test
```

#### The framework has requires you to have a postgres server running locally. The OpenWeatherAPi table has:
```text
[Id][ApiKey]
[1 | api key]
```

#### You also need to create a text file that has the jdbc postgres connection string:
```text
jdbc:postgresql://localhost:[your postgres port number]/OpenWeatherApi?user=yourusername&password=yourpassword
```

#### Example:
```java
public class Foo {

    private final IOpenWeatherDbReader reader;
    private final Faker faker;

    public Foo(){
        IConfigFileReader configFileReader = new ConfigFileReader();

        reader = new OpenWeatherDbReader(configFileReader.getConfigFromFile("[full file path].txt"));

        faker = new Faker();
    }
}
```

## Pipeline Setup
A GitHub Actions pipeline has been set up to automate the build and testing process. The pipeline configuration (yaml file) can be found in the .github/workflows directory of this repository. Here’s a basic example of a pipeline configuration:

```yaml
    name: CI
    
    on:
    push:
        branches:
        - main
    pull_request:
        branches:
        - main
    
    jobs:
    build:
        runs-on: ubuntu-latest
    
        steps:
        - name: Checkout repository
          uses: actions/checkout@v2
    
        - name: Set up JDK 11
          uses: actions/setup-java@v2
          with:
            java-version: '11'
    
        - name: Build with Maven
          run: mvn clean install
    
        - name: Run tests
          run: mvn test
```
