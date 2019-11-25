package com.ahok.cuber.service;

import com.ahok.cuber.entity.Trip;

import java.util.List;

public interface TripService {

    List<Trip> getAllTrips();

    Trip getTrip(String tripId);

    String createTrip(Trip trip);

    String updateTrip(Trip trip);

    String deleteTrip(String tripId);
}
