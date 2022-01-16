package com.emon.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/temp")
public class WeatherController {

    @Autowired
    WeatherReportHandler wrh;

    @GetMapping("/min")
    @ResponseBody
    public ResponseEntity<Map<String,Integer>> getMin(){
        return new ResponseEntity<>(wrh.getMinTemperature(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/avg")
    @ResponseBody
    public ResponseEntity<Map<String,Integer>> getAvg(){
        return new ResponseEntity<>(wrh.getAvgTemperature(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/max")
    @ResponseBody
    public ResponseEntity<Map<String,Integer>> getMax(){
        return new ResponseEntity<>(wrh.getMaxTemperature(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/current")
    @ResponseBody
    public ResponseEntity<WeatherInfo> getCurrent(){
        return new ResponseEntity<>(wrh.getCurrentWeatherInfo(), HttpStatus.ACCEPTED);
    }
}
