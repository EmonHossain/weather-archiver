package com.emon.weather;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Implements commandLineRunner interface.
 * Run right after running the application.
 * Child Thread.
 * This component won't run if test profile active.
 */
@Profile("!test")
@Component
@Slf4j
public class WeatherReportThread implements CommandLineRunner {

    @Autowired
    private WeatherReportHandler weatherReportHandler;

    //Runs continuously unless thread interrupted
    @Override
    public void run(String... args){
        while (!Thread.currentThread().isInterrupted()){
            try {
                weatherReportHandler.updateWeatherReport();
                //goes into sleep for 30 minutes
                Thread.sleep(30*60*1000L);
            }
            catch (InterruptedException ie){
                log.error("Data fetch thread Interrupted");
                log.error(ie.getMessage(),ie);
                Thread.currentThread().interrupt();
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
    }



}
