package com.ahok.cuber.socket.pojo;

public class TripRequest {

    private String clientLocation;
    private String clientDestination;
    private String driver;

    public TripRequest() {
    }

    public TripRequest(String driver, String clientLocation, String clientDestination) {
        super();
        this.driver = driver;
        this.clientLocation = clientLocation;
        this.clientDestination = clientDestination;
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

    public String getClientDestination() {
        return clientDestination;
    }

    public void setClientDestination(String clientDestination) {
        this.clientDestination = clientDestination;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "TripRequest{" +
                "clientLocation='" + clientLocation + '\'' +
                ", clientDestination='" + clientDestination + '\'' +
                ", driver='" + driver + '\'' +
                '}';
    }
}