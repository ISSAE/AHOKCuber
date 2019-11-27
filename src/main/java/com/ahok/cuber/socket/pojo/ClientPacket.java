package com.ahok.cuber.socket.pojo;
import com.ahok.cuber.pojo.ClientPojo;

public class ClientPacket {
    private String location;
    private ClientPojo client;

    public ClientPacket() {
    }

    public ClientPacket(ClientPojo client, String location) {
        super();
        this.client = client;
        this.location = location;
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

    @Override
    public String toString() {
        return "ClientPacket{" +
                "location='" + location + '\'' +
                ", client=" + client +
                '}';
    }
}
