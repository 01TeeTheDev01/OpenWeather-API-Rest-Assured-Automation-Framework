# Weather API Testing Framework

## Description
This project aims to develop a testing framework for the OpenWeatherMap API (http://api.openweathermap.org). The framework will automate testing of various HTTP methods such as POST, GET, PUT, and DELETE for different endpoints provided by the API.

## Table of Contents
- [Introduction](#weather-api-testing-framework)
- [Setup](#setup)
- [Endpoints Tested](#endpoints-tested)
- [Tools Used](#tools-used)
- [Usage](#usage)
- [Pipeline Setup](#pipeline-setup)
- [License](#license)

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

- Maven: Build tool and dependency management for the project.
- TestNG: Testing framework for unit and integration tests in Java.
- GitHub Actions: CI/CD pipeline for automating builds, tests, and deployments.

## Usage
Run the below command in the project source folder to run all tests:
```shell
mvn test
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

## License
This project is licensed under the MIT License.