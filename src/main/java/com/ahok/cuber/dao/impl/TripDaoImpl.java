package com.ahok.cuber.dao.impl;

import com.ahok.cuber.dao.TripDao;
import com.ahok.cuber.entity.Trip;
import com.ahok.cuber.util.HibernateHelper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TripDaoImpl implements TripDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Trip> getAll() {
        return HibernateHelper.list(sessionFactory, Trip.class);
    }

    @Transactional
    public String insertNew(Trip trip) {
        return (String) sessionFactory.getCurrentSession().save(trip);
    }

    @Transactional
    public Trip get(String tripId) {
        return sessionFactory.getCurrentSession().get(Trip.class, tripId);
    }

    @Transactional
    public String update(Trip trip) {
        sessionFactory.getCurrentSession().update(trip);
        return "Trip information updated successfully";
    }

    @Transactional
    public String remove(String tripId) {
        Trip trip = sessionFactory.getCurrentSession().get(Trip.class, tripId);
        sessionFactory.getCurrentSession().delete(trip);
        return "Trip with id " + trip.getId() + " deleted successfully";
    }
}
