package com.ahok.cuber.socket.modules.chat;

import com.ahok.cuber.socket.SocketService;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverModule {

    private static final Logger log = LoggerFactory.getLogger(DriverModule.class);

    private final SocketIONamespace namespace;

    @Autowired
    public DriverModule(SocketService socketService) {
        SocketIOServer server = socketService.getServer();
        this.namespace = server.addNamespace("/driver");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("get_location", DriverLocation.class, onLocationReceived());
    }

    private DataListener<DriverLocation> onLocationReceived() {
        return (client, data, ackSender) -> {
            log.debug("Client[{}] - Received location data '{}'", client.getSessionId().toString(), data);
            System.out.println(data);
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            log.debug("Client[{}] - Connected to driver module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> log.debug("Client[{}] - Disconnected from driver module.", client.getSessionId().toString());
    }

}