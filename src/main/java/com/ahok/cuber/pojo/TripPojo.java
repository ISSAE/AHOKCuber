package com.ahok.cuber.pojo;

import com.ahok.cuber.entity.Trip;
import java.util.Date;

public class TripPojo {
	private String id;
    private Float distance;
    private Date started_at;
    private Date ended_at;
    private Float price;
    private Float estimated_time;
    private String start_location;
    private String end_location;
    private Trip.Status status;
    private ClientPojo client;
    private DriverPojo driver;
    private Date created_at;
    private Date updated_at;

    public TripPojo (Trip trip) {
        this.id = trip.getId();
        this.distance = trip.getDistance();
        this.started_at = trip.getStarted_at();
        this.ended_at = trip.getEnded_at();
        this.price = trip.getPrice();
        this.estimated_time = trip.getEstimated_time();
        this.start_location = trip.getStart_location();
        this.end_location = trip.getEnd_location();
        this.status = trip.getStatus();
        this.client = new ClientPojo(trip.getClient());
        this.driver = new DriverPojo(trip.getDriver());
        this.created_at = trip.getCreated_at();
        this.updated_at = trip.getUpdated_at();
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", distance=" + distance +
                ", started_at=" + started_at +
                ", ended_at=" + ended_at +
                ", price=" + price +
                ", estimated_time=" + estimated_time +
                ", start_location='" + start_location + '\'' +
                ", end_location='" + end_location + '\'' +
                ", status=" + status +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientPojo getClient() {
        return client;
    }

    public void setClient(ClientPojo client) {
        this.client = client;
    }

    public DriverPojo getDriver() {
        return driver;
    }

    public void setDriver(DriverPojo driver) {
        this.driver = driver;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Date getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Date started_at) {
        this.started_at = started_at;
    }

    public Date getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Date ended_at) {
        this.ended_at = ended_at;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(Float estimated_time) {
        this.estimated_time = estimated_time;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public Trip.Status getStatus() {
        return status;
    }

    public void setStatus(Trip.Status status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
