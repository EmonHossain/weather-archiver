package com.emon.weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WeatherReportHandlerTest {

    @Mock
    private WeatherRepository repository;

    @InjectMocks
    private WeatherReportHandler weatherReportHandler;


    @Test
    public void maxTemperatureShouldReturnMap(){
        when(repository.findMaxTemp()).thenReturn(5);
        Map<String,Integer> map=  weatherReportHandler.getMaxTemperature();
        assertThat(map).containsKey("maximum_temp");
    }

    @Test
    public void minTemperatureShouldReturnMap(){
        when(repository.findMinTemp()).thenReturn(-2);
        Map<String,Integer> map=  weatherReportHandler.getMinTemperature();
        assertThat(map).containsKey("minimum_temp");
    }


}
