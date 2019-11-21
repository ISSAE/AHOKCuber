package com.ahok.cuber.socket.modules;

public class SocketUser {

    private String userID;
    private String sessionID;
    private boolean isDriver;

    public SocketUser() {}

    public SocketUser(String token, String sessionID) {
        super();
        this.userID = token;
        this.sessionID = sessionID;
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