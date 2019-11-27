package com.ahok.cuber.socket.service;

import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.service.DriverService;
import com.ahok.cuber.service.TripService;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocketService {
    private SocketIOServer server;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TripService tripService;

    public SocketService() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        this.server = new SocketIOServer(config);
        this.server.start();
    }

    public SocketIOServer getServer() {
        return this.server;
    }

    public DriverService getDriverService() {
        return driverService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public TripService getTripService() {
        return tripService;
    }

    public <T> void broadCastToRoom(T object, String roomName, String event) {
        this.getServer().getNamespace("/driver").getRoomOperations(roomName).sendEvent("trip_updated", object);
        this.getServer().getNamespace("/client").getRoomOperations(roomName).sendEvent("trip_updated", object);
    }
}
