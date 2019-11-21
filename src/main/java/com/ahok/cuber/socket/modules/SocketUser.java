package com.ahok.cuber.socket.modules;

public class SocketUser {

    private String token;
    private String sessionID;

    public SocketUser() {
    }

    public SocketUser(String token, String sessionID) {
        super();
        this.token = token;
        this.sessionID = sessionID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
                "token='" + token + '\'' +
                ", sessionID='" + sessionID + '\'' +
                '}';
    }
}