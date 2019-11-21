package com.ahok.cuber.util;

import com.ahok.cuber.socket.modules.SocketUser;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;

public class SocketUtil {

    public static SocketIOClient get(SocketIONamespace namespace, String ownerID) {
        for (SocketIOClient client : namespace.getAllClients()) {
            if (client.get("ownerID").equals(ownerID)) return client;
        }
        return null;
    }

    public static SocketUser auth(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        SocketUser user = new SocketUser();
        user.setSessionID(client.getSessionId().toString());
        user.setToken(token);

        return user;
    }
}
