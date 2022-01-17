package com.emon.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


/**
 * java POJO also ORM.
 * annotated to create database table and column automatically.
 * avoided boilerplate code. 'Lombok' will generate required methods automatically.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonRootName("current_weather")
public class WeatherInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    //primary key
    private Long id;
    private int temperature;
    private int feelsLike;
    @Transient
    @JsonProperty("temp_scale")
    private String scale="Celsius";
    //omit this field wile saving data
    @Transient
    @JsonProperty("localObsDateTime")
    private String jsonTime;
    //does not produce JSON value
    @JsonIgnore
    private long observeTime;
    private String description;


    public WeatherInfo(int temp, int feels, long time, String desc){
        this.temperature = temp;
        this.feelsLike = feels;
        this.observeTime = time;
        this.description = desc;
    }
}
