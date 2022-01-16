package com.emon.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@Profile("!test")
@Component
public class WeatherReportThread implements CommandLineRunner {

    @Autowired
    private WeatherReportHandler weatherReportHandler;

    @Override
    public void run(String... args){
        while (!Thread.currentThread().isInterrupted()){
            try {
                weatherReportHandler.updateWeatherReport();
                Thread.sleep(10000);
            }
            catch (InterruptedException IE){
                Thread.currentThread().interrupt();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
