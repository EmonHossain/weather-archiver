package com.emon.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * All business logic
 *
 */
@Service
@Slf4j
public class WeatherReportHandler {
    //Assign values from application.properties file
    @Value("${weather.remote.url}")
    private String URL;

    @Value("${weather.remote.url.response.format}")
    private String responseFormat;

    @Value("${weather.remote.response.json.filedName}")
    private String currentStatus;

    //Dependency injection
    @Autowired
    private Utils utils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherRepository repository;

    /**
     * Fetch weather data from remote URL asynchronously
     */
    @Async
    public CompletableFuture<Boolean> updateWeatherReport() throws Exception {

        WeatherInfo wi = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        //formatting URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL).queryParam("format", responseFormat);
        log.info("pinging at :  "+URL);
        //Request initialized and process response as expected format
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,requestEntity,String.class);
        log.info("Response status : "+response.getStatusCode().name());

        if(response.getStatusCode()!= HttpStatus.OK){
            log.error("Invalid response from : "+URL);
            throw new Exception("Invalid response from remote URL");
        }
        try {
            JsonNode node = objectMapper.readTree(response.getBody());

            //extract data form JSON response
            int temp = node.get(currentStatus).get(0).get("temp_C").asInt();
            int feelsLike = node.get(currentStatus).get(0).get("FeelsLikeC").asInt();
            String localObsTime = node.get(currentStatus).get(0).get("localObsDateTime").asText();
            String description = node.get(currentStatus).get(0).get("weatherDesc").get(0).get("value").asText();

            log.info("Data fetch complete");

            long millis = utils.formatDateStringToMilli(localObsTime);

            /**
             * compare fetched data with previous data
             * if data unchanged, never save into database
             */
            if(isNewReport(millis)){
                wi = new WeatherInfo(temp, feelsLike, millis, description);
                repository.save(wi);
                log.info("Data store operation successful");
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return CompletableFuture.completedFuture(true);
    }

    public Map<String, String> getMinTemperature(){
        Map<String, String> map = new HashMap<>();
        map.put("minimum_temp",String.valueOf(repository.findMinTempFor(24)));
        map.put("temp_scale","Celsius");
        return map;
    }

    public Map<String, String> getAvgTemperature(){
        Map<String, String> map = new HashMap<>();
        map.put("average_temp",String.valueOf(repository.findAvgTempFor(24)));
        map.put("temp_scale","Celsius");
        return map;
    }

    public Map<String, String> getMaxTemperature(){
        Map<String, String> map = new HashMap<>();
        map.put("maximum_temp",String.valueOf(repository.findMaxTempFor(24)));
        map.put("temp_scale","Celsius");
        return map;
    }

    public WeatherInfo getCurrentWeatherInfo() {
        WeatherInfo wi = repository.findCurrent();
        wi.setJsonTime(utils.millisToDateTime(wi.getObserveTime()));
        return wi;
    }

    private boolean isNewReport(long milliseconds){
        if(repository.findCurrent() == null){
            return true;
        }
        else if(repository.findCurrent().getObserveTime()<milliseconds) {
            return true;
        }
        return false;
    }
}
