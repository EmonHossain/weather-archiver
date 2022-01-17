package com.emon.weather;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class WeatherReportHandlerTest {

    //Mock repository service
    @Mock
    private WeatherRepository repository;

    @InjectMocks
    private WeatherReportHandler weatherReportHandler;


    @Test
    public void maxTemperatureShouldReturnMap(){
        //mocking method return result
        //must run before calling dependent service
        when(repository.findMaxTempFor(24)).thenReturn(5);

        Map<String,String> map=  weatherReportHandler.getMaxTemperature();
        assertThat(map).containsKey("maximum_temp");
    }

    @Test
    public void minTemperatureShouldReturnMap(){
        //mocking method return result
        //must run before calling dependent service
        when(repository.findMinTempFor(24)).thenReturn(-2);

        Map<String,String> map=  weatherReportHandler.getMinTemperature();
        assertThat(map).containsKey("minimum_temp");
    }


}
