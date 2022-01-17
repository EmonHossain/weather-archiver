package com.emon.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Pull and push data into database
 */
@Repository
@Transactional
public class WeatherRepository {

    private WeatherInfo currentRecord;

    @Autowired
    private Utils utils;

    @Autowired
    private EntityManager entityManager;

    //Save weather information into weather table
    public void save(WeatherInfo info){
        entityManager.persist(info);
        this.currentRecord = info;
    }

    public WeatherInfo getCurrentRecord(){
        return this.currentRecord;
    }

    public WeatherInfo findCurrent(){
        return getCurrentRecord();
    }

    //pull all record from database
    public List<WeatherInfo> findAll(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeatherInfo> criteria = builder.createQuery( WeatherInfo.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);
        criteria.select( root );
        return entityManager.createQuery(criteria).getResultList();
    }

    //run aggregate operations to find maximum
    public int findMaxTempFor(int hour){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = builder.createQuery( Integer.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);

        criteria.select( builder.max(root.get("temperature")));
        criteria.where(builder.greaterThanOrEqualTo(root.get("observeTime"),utils.getTimeBeforeHoursFromNowInMillis(hour)));
        return entityManager.createQuery(criteria).getSingleResult();
    }

    public int findAvgTempFor(int hour){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> criteria = builder.createQuery( Double.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);

        criteria.select( builder.avg(root.get("temperature")));
        criteria.where(builder.greaterThanOrEqualTo(root.get("observeTime"),utils.getTimeBeforeHoursFromNowInMillis(hour)));
        double avg = entityManager.createQuery(criteria).getSingleResult();
        return (int) avg;
    }

    public int findMinTempFor(int hour){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = builder.createQuery( Integer.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);

        criteria.select( builder.min(root.get("temperature")));
        criteria.where(builder.greaterThanOrEqualTo(root.get("observeTime"),utils.getTimeBeforeHoursFromNowInMillis(hour)));
        return entityManager.createQuery(criteria).getSingleResult();
    }

}
