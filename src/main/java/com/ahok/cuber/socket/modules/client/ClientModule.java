package com.ahok.cuber.socket.modules.client;

import com.ahok.cuber.socket.SocketService;
import com.ahok.cuber.socket.modules.SocketUser;
import com.ahok.cuber.util.SocketUtil;
import com.corundumstudio.socketio.SocketIOClient;
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
        this.namespace.addEventListener("request_location", Object.class, onRequestLocation());
    }

    private DataListener<Object> onRequestLocation() {
        return (client, data, ackSender) -> {
            SocketUser user = SocketUtil.auth(client);
            System.out.println(String.format("Client[%s] - Request Candidate Driver location.\n", client.getSessionId().toString()));
            this.server.getNamespace("/driver").getBroadcastOperations().sendEvent("request_location", user);
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            String token = client.getHandshakeData().getSingleUrlParam("token");
            SocketUser user = SocketUtil.auth(client);
            if (user != null) {
                if (user.isDriver()) {
                    disconnect(client, "Driver cannot login to client module");
                    return;
                }
                client.set("ownerID", user.getUserID());

                System.out.printf("Client[%s] - Connected to client module with token => '%s'\n", client.getSessionId().toString(), token);
                System.out.printf("Number of connected clients '%s'\n", namespace.getAllClients().size());
            } else {
                disconnect(client, "Invalid Token");
            }
        };
    }

    private void disconnect (SocketIOClient client, String message) {
        client.sendEvent("invalid_token");
        System.out.println(message);
        client.disconnect();
    }

    private DisconnectListener onDisconnected() {
        return client -> System.out.printf("Client[%s] - Disconnected from client module.\n", client.getSessionId().toString());
    }

}