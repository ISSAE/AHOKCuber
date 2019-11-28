package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Trip;
import com.ahok.cuber.pojo.DriverPojo;
import com.ahok.cuber.pojo.TripPojo;
import com.ahok.cuber.service.TripService;
import com.ahok.cuber.util.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/all",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getAllTrips() {
        List<Trip> tripsList =
                tripService
                        .getAllTrips();

        List<TripPojo> trips = new ArrayList<>();
        tripsList.forEach(trip -> trips.add(new TripPojo(trip)));

        return Response.ok(trips);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/get/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getTrip(@PathVariable("id") String tripId) {
        return Response.ok(new TripPojo(tripService.getTrip(tripId)));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/add",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity createTrip(@RequestBody @Validated Trip trip) {
        tripService.createTrip(trip);
        return Response.ok(new TripPojo(trip));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/update",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.PUT)
    public ResponseEntity updateTrip(@RequestBody @Validated Trip trip) {
        // TODO
        // handle IllegalArgumentException if `id` is missing.
        // handle NullPointerException if trip with given `id` not found
        return Response.ok(tripService.updateTrip(trip));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/delete/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteTrip(@PathVariable("id") String tripId) {
        return Response.ok(tripService.deleteTrip(tripId));
    }
}
