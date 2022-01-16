package com.emon.weather;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

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
    private Long id;
    private int temperature;
    private int feelsLike;
    @Transient
    @JsonProperty("localObsDateTime")
    private String jsonTime;
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
