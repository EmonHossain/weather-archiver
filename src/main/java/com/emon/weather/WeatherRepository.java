package com.emon.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class WeatherRepository {

    private WeatherInfo currentRecord;

    @Autowired
    private Utils utils;

    @Autowired
    private EntityManager entityManager;

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

    public List<WeatherInfo> findAll(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeatherInfo> criteria = builder.createQuery( WeatherInfo.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);
        criteria.select( root );
        return entityManager.createQuery(criteria).getResultList();
    }

    public int findMaxTemp(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = builder.createQuery( Integer.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);

        criteria.select( builder.max(root.get("temperature")));
        criteria.where(builder.greaterThanOrEqualTo(root.get("observeTime"),utils.getTimeBeforeHoursFromNowInMillis(24)));
        return entityManager.createQuery(criteria).getSingleResult();
    }

    public int findAvgTemp(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> criteria = builder.createQuery( Double.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);

        criteria.select( builder.avg(root.get("temperature")));
        criteria.where(builder.greaterThanOrEqualTo(root.get("observeTime"),utils.getTimeBeforeHoursFromNowInMillis(24)));
        double avg = entityManager.createQuery(criteria).getSingleResult();
        return (int) avg;
    }

    public int findMinTemp(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = builder.createQuery( Integer.class );
        Root<WeatherInfo> root = criteria.from(WeatherInfo.class);

        criteria.select( builder.min(root.get("temperature")));
        criteria.where(builder.greaterThanOrEqualTo(root.get("observeTime"),utils.getTimeBeforeHoursFromNowInMillis(24)));
        return entityManager.createQuery(criteria).getSingleResult();
    }

}
