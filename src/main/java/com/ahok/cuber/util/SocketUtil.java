package com.ahok.cuber.util;

import com.ahok.cuber.socket.pojo.SocketUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
            DecodedJWT jwt = SocketUtil.getTokenDecoder(token);
            Claim isDriver = jwt.getHeaderClaim("isDriver");
            Claim ownerID = jwt.getHeaderClaim("ownerID");

            SocketUser user = new SocketUser();
            user.setSessionID(client.getSessionId().toString());
            user.setUserID(ownerID.asString());
            user.setDriver(isDriver.asBoolean());

            return user;
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    private static DecodedJWT getTokenDecoder(String token) {
        Algorithm algorithm = Algorithm.HMAC256(Config.getProperty("AUTH_PASSPHRASE"));
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("cuber")
                .build();
        return verifier.verify(token);
    }
}
