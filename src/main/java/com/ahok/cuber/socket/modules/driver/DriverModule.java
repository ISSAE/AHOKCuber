package com.ahok.cuber.socket.modules.driver;

import com.ahok.cuber.socket.SocketService;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverModule {
    private final SocketIONamespace namespace;

    private final SocketIOServer server;

    @Autowired
    public DriverModule(SocketService socketService) {
        this.server = socketService.getServer();
        this.namespace = server.addNamespace("/driver");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("get_location", DriverLocation.class, onLocationReceived());
    }

    private DataListener<DriverLocation> onLocationReceived() {
        return (driver, data, ackSender) -> {
            System.out.printf("Driver[%s] - Received location data '%s'\n", driver.getSessionId().toString(), data);
            this.server.getNamespace("/client").getBroadcastOperations().sendEvent("receive_location", data);
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            System.out.printf("Driver[%s] - Connected to driver module through '%s'\n", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> System.out.printf("Driver[%s] - Disconnected from driver module.\n", client.getSessionId().toString());
    }

}