package com.ahok.cuber.socket.modules.client;

public class ClientLocation {

    private String userName;
    private String message;

    public ClientLocation() {
    }

    public ClientLocation(String userName, String message) {
        super();
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DriverLocation{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}