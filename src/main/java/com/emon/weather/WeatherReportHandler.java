package com.emon.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class WeatherReportHandler {

    @Value("${weather.remote.url}")
    private String URL;
    @Value("${weather.remote.url.response.format}")
    private String responseFormat;

    @Value("${weather.remote.response.json.filedName}")
    private String currentStatus;

    @Autowired
    private Utils utils;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherRepository repository;

    @Async
    public CompletableFuture<Boolean> updateWeatherReport() throws Exception {
        WeatherInfo wi = null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL).queryParam("format", responseFormat);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,requestEntity,String.class);

        if(response.getStatusCode()!= HttpStatus.OK){
            throw new Exception("Invalid response from remote URL");
        }try {
            JsonNode node = objectMapper.readTree(response.getBody());

            int temp = node.get(currentStatus).get(0).get("temp_C").asInt();
            int feelsLike = node.get(currentStatus).get(0).get("FeelsLikeC").asInt();
            String localObsTime = node.get(currentStatus).get(0).get("localObsDateTime").asText();
            String description = node.get(currentStatus).get(0).get("weatherDesc").get(0).get("value").asText();

            long millis = utils.formatDateStringToMilli(localObsTime);

            if(isNewReport(millis)){
                wi = new WeatherInfo(temp, feelsLike, millis, description);
                repository.save(wi);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(true);
    }

    public Map<String, Integer> getMinTemperature(){
        Map<String, Integer> map = new HashMap<>();
        map.put("minimum_temp",repository.findMinTemp());
        return map;
    }

    public Map<String, Integer> getAvgTemperature(){
        Map<String, Integer> map = new HashMap<>();
        map.put("average_temp",repository.findAvgTemp());
        return map;
    }

    public Map<String, Integer> getMaxTemperature(){
        Map<String, Integer> map = new HashMap<>();
        map.put("maximum_temp",repository.findMaxTemp());
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
