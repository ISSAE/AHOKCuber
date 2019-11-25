package com.ahok.cuber.dao;

import com.ahok.cuber.entity.Trip;

import java.util.List;

public interface TripDao {
    List<Trip> getAll();
    Trip get(String userId);
    String insertNew(Trip user);
    String update(Trip user);
    String remove(String userId);
}
