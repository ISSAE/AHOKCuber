package com.ahok.cuber.util;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;

public class SocketClient {
    public static SocketIOClient get(SocketIONamespace namespace, String ownerID) {
        for (SocketIOClient client : namespace.getAllClients()) {
            if (client.get("ownerID").equals(ownerID)) return client;
        }
        return null;
    }
}
