package com.ahok.cuber.socket.pojo;
import com.ahok.cuber.pojo.DriverPojo;

public class DriverPacket {
    private String location;
    private DriverPojo driver;

    public DriverPacket() {
    }

    public DriverPacket(DriverPojo driver, String location) {
        super();
        this.driver = driver;
        this.location = location;
    }

    public DriverPojo getDriver() {
        return driver;
    }

    public void setDriver(DriverPojo driver) {
        this.driver = driver;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "DriverPacket{" +
                "location='" + location + '\'' +
                ", driver=" + driver +
                '}';
    }
}
