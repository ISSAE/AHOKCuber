package com.ahok.cuber.util;

import com.ahok.cuber.socket.modules.SocketUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
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
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim isDriver = jwt.getClaim("isDriver");
            Claim ownerID = jwt.getClaim("ownerID");

            SocketUser user = new SocketUser();
            user.setSessionID(client.getSessionId().toString());
            user.setUserID(ownerID.asString());
            user.setDriver(isDriver.asBoolean());

            return user;
        } catch (JWTDecodeException exception) {
            return null;
        }
    }
}
