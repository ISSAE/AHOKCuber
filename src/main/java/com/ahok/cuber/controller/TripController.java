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

/**
 * Rest CRUD Trip Controller
 * <p>
 * Available endpoints:
 * <ul>
 * <li><b>GET</b>    trips/all            Get a list of all {@link Trip}s</li>
 * <li><b>GET</b>    trips/get/{id}       Get {@link Trip} by id</li>
 * <li><b>POST</b>   trips/add            Add new {@link Trip}</li>
 * <li><b>PUT</b>    trips/update         Update {@link Trip}</li>
 * <li><b>DELETE</b> trips/delete/{id}    Delete {@link Trip}</li>
 * </ul>
 */
@RestController
public class TripController {

    /**
     * Hibernate Service to manage Trip Module
     */
    @Autowired
    private TripService tripService;

    /**
     * Get All Trips
     *
     * Method: GET
     *
     * @return tripsList TripPojo
     */
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

    /**
     * Get Trip by ID
     * <p>
     * Method: GET
     *
     * @param tripId PathVariable String
     *
     * @return trip TripPojo
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/get/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getTrip(@PathVariable("id") String tripId) {
        return Response.ok(new TripPojo(tripService.getTrip(tripId)));
    }

    /**
     * Create new Trip
     * <p>
     * Method: POST
     *
     * @param trip Trip
     *
     * @return trip TripPojo
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/add",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity createTrip(@RequestBody @Validated Trip trip) {
        tripService.createTrip(trip);
        return Response.ok(new TripPojo(trip));
    }

    /**
     * Update Trip
     * <p>
     * Method: PUT
     *
     * @param trip Trip
     *
     * @return trip TripPojo
     */
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

    /**
     * Delete Trip by id
     * <p>
     * Method: DELETE
     *
     * @param tripId PathVariable String
     *
     * @return message String
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "trips/delete/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteTrip(@PathVariable("id") String tripId) {
        return Response.ok(tripService.deleteTrip(tripId));
    }
}
