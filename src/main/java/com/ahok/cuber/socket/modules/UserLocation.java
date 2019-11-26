package com.ahok.cuber.socket.modules;

public class UserLocation {

    private String location;
    private SocketUser user;

    public UserLocation() {
    }

    public UserLocation(SocketUser user, String location) {
        super();
        this.user = user;
        this.location = location;
    }

    public SocketUser getUser() {
        return user;
    }

    public void setUser(SocketUser user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "location='" + location + '\'' +
                ", user=" + user +
                '}';
    }
}