package com.emon.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Handle and control all request from user and response accordingly in JSON format
 */
@RestController
@RequestMapping("/")
public class WeatherController {

    @Autowired
    private WeatherReportHandler wrh;

    //Handle request with different URI
    @GetMapping({"/","/temp/current"})
    public ResponseEntity<WeatherInfo> getCurrent(){
        return new ResponseEntity<>(wrh.getCurrentWeatherInfo(), HttpStatus.OK);
    }

    @GetMapping("/temp/min")
    public ResponseEntity<Map<String,String>> getMin(){
        return new ResponseEntity<>(wrh.getMinTemperature(), HttpStatus.OK);
    }

    @GetMapping("/temp/avg")
    public ResponseEntity<Map<String,String>> getAvg(){
        return new ResponseEntity<>(wrh.getAvgTemperature(), HttpStatus.OK);
    }

    @GetMapping("/temp/max")
    public ResponseEntity<Map<String,String>> getMax(){
        return new ResponseEntity<>(wrh.getMaxTemperature(), HttpStatus.OK);
    }

}
