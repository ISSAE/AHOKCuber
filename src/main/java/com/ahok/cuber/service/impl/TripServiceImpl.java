package com.ahok.cuber.service.impl;

import com.ahok.cuber.dao.TripDao;
import com.ahok.cuber.entity.Trip;
import com.ahok.cuber.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripDao tripDao;

    @Override
    public List<Trip> getAllTrips() {
        return tripDao.getAll();
    }

    @Override
    public Trip getTrip(String tripId) {
        return tripDao.get(tripId);
    }

    @Override
    public String createTrip(Trip trip) {
        return tripDao.insertNew(trip);
    }

    @Override
    public String updateTrip(Trip trip) {
        return tripDao.update(trip);
    }

    @Override
    public String deleteTrip(String tripId) {
        return tripDao.remove(tripId);
    }
}
