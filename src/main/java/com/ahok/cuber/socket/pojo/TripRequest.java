package com.ahok.cuber.socket.pojo;

public class TripRequest {

    private String clientLocation;
    private String driver;

    public TripRequest() {
    }

    public TripRequest(String driver, String clientLocation) {
        super();
        this.driver = driver;
        this.clientLocation = clientLocation;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "TripRequest{" +
                "clientLocation='" + clientLocation + '\'' +
                ", driver=" + driver +
                '}';
    }
}