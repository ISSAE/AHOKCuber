package com.ahok.cuber.socket.pojo;

public class SocketUser {

    private String userID;
    private String sessionID;
    private boolean isDriver;

    public SocketUser() {}

    public SocketUser(String userID, String sessionID, boolean isDriver) {
        this.userID = userID;
        this.sessionID = sessionID;
        this.isDriver = isDriver;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String token) {
        this.userID = token;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public String toString() {
        return "SocketUser{" +
                "token='" + userID + '\'' +
                ", sessionID='" + sessionID + '\'' +
                '}';
    }
}