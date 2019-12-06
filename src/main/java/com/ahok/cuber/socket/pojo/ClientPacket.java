package com.ahok.cuber.socket.pojo;
import com.ahok.cuber.pojo.ClientPojo;

public class ClientPacket {
    private String location;
    private String destination;
    private ClientPojo client;

    public ClientPacket() {
    }

    public ClientPacket(ClientPojo client, String location, String destination) {
        super();
        this.client = client;
        this.location = location;
        this.destination = destination;
    }

    public ClientPojo getClient() {
        return client;
    }

    public void setClient(ClientPojo client) {
        this.client = client;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "ClientPacket{" +
                "location='" + location + '\'' +
                ", destination='" + destination + '\'' +
                ", client=" + client +
                '}';
    }
}
