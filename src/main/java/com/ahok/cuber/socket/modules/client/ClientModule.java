package com.ahok.cuber.socket.modules.client;

import com.ahok.cuber.socket.SocketService;
import com.ahok.cuber.socket.modules.SocketUser;
import com.ahok.cuber.socket.modules.driver.DriverLocation;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientModule {
    private final SocketIONamespace namespace;

    private final SocketIOServer server;

    @Autowired
    public ClientModule(SocketService socketService) {
        this.server = socketService.getServer();
        this.namespace = server.addNamespace("/client");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("request_location", SocketUser.class, onRequestLocation());
    }

    private DataListener<SocketUser> onRequestLocation() {
        return (client, data, ackSender) -> this.server.getNamespace("/driver").getBroadcastOperations().sendEvent("request_location", data);
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            System.out.printf("Client[%s] - Connected to client module through '%s'\n", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> System.out.printf("Client[%s] - Disconnected from client module.\n", client.getSessionId().toString());
    }

}