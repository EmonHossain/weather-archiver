# Archiving Weather Data

Create an application which periodically fetches weather data from [wttr.in](https://wttr.in/)'s JSON API and saves it in a database. 

You needn't save all available data, but at the very least, save the current temperature.

The application must provide a REST API to query for aggregated values over the saved data, e.g. getting the max/min/avg temperature of the past 24 hours.

The application must be runnable using Docker. The image needn't be uploaded to a registry, providing a Dockerfile is sufficient. You may also run provide a docker-compose file to start both your application and the database.

## Technology Choice

The technology choice is up to you. We expect to run only standard Docker(-Compose) commands to run it (e.g. `docker build`, `docker run`, `docker-compose up`).

## Final Remarks

If in doubt, ask!



# Solution

Weather Data Archiver is a RESTful web service developed in [java](https://www.java.com) and based on [spring framework](https://www.spring.io).
In every 30 minutes actual weather data collected from [wttr.in](https://www.wttr.in/) and stored into [mySQL](https://www.mysql.com/) database and provides interested weather information to user in JSON format.

## How to use


The service source code can be build and package like simple spring boot project or install as it is using provided docker compose. In either ways you need to clone or download this repository first.
### ***Build*** and ***Run*** from source code:
#### ***Minimum Requirements:***
* JDK Version-17.x.x
* maven Version-3.8.x or latest
* mySQL Version-8.x.x

#### ***Project Structure:***
```
+---src
+---main
    |   +---java
    |   |   \---com
    |   |       \---emon
    |   |           \---weather
    |   \---resources
    |       +---static
    |       \---templates
    \---test
        \---java
            \---com
                \---emon
                    \---weather
```
### Run with ***Docker:*** 
#### ***Minimum Requirements:***
* Docker installed in your machine 
  * check you have installed and running Docker in your machine using the ```docker info``` command or install docker from [here](https://www.docker.com/)
#### ***Run:***
To run this service you don't need to worry about anything. Just use your ```CMD``` or ```Linux Terminal```) Only be sure you are in project's root directory. In root directory you will find ```docker-compose.yml``` 
file. Now enter the `docker-compose up` using your Terminal or CMD, The Docker composer file will take care everything, and you are good to go.When containers are up and running use `-curl` or `web browser` or use `postman` to check the application.

### REST APIs Description:
[N.B. All responses are in JSON format]
1. Root URL `http://localhost:8080/` method `GET` Response type `JSON`
   * Simply providing `http://localhost:8080/` will give trimmed down `current weather` information.
    ```
   {
    "temperature": 6,
    "feelsLike": 3,
    "description": "Sunny",
    "localObsDateTime": "2022-01-14 01:46 PM"
   }
   ```
2. `/`or`/temp/current`, Method `GET`, Response type `JSON`
   * also give the current weather information like above Root URL

  
3. `/temp/min`, Method `GET`, Response type `JSON`
   * Provide `Minimum` temperature in last 24 hours
   ```
   {
    "minimum_temp": 2
   }
   ```

4. `/temp/avg`, Method `GET`, Response type `JSON`
   * Provide `Average` temperature in last 24 hours
   ```
   {
    "average_temp": 4
   }
   ```
5. `/temp/max`, Method `GET`, Response type `JSON`
   * Provide `Maximum` temperature in last 24 hours
   ```
   {
    "average_temp": 2
   }
   ```
