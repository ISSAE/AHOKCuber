package com.ahok.cuber.socket.modules.client;

import com.ahok.cuber.socket.SocketService;
import com.ahok.cuber.socket.modules.SocketUser;
import com.ahok.cuber.util.Config;
import com.ahok.cuber.util.SocketUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
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
        this.namespace.addEventListener("request_location", SocketUser.class, onRequestLocation());
    }

    private DataListener<SocketUser> onRequestLocation() {
        return (client, data, ackSender) -> {
            SocketUser user = SocketUtil.auth(client);
            this.server.getNamespace("/driver").getBroadcastOperations().sendEvent("request_location", user);
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            String token = client.getHandshakeData().getSingleUrlParam("token");
            try {
                Algorithm algorithm = Algorithm.HMAC256(Config.getProperty("AUTH_PASSPHRASE"));
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("cuber")
                        .build();
                DecodedJWT jwt = verifier.verify(token);
                Claim isDriver = jwt.getClaim("isDriver");
                Claim ownerID = jwt.getClaim("ownerID");

                if (isDriver.asBoolean()) {
                    this.disconnect(client, "Driver cannot login to the client module");
                    return;
                }

                client.set("ownerID", ownerID.asString());

                System.out.printf("Client[%s] - Connected to client module with token => '%s'\n", client.getSessionId().toString(), token);
            } catch (JWTDecodeException exception) {
                this.disconnect(client, "Invalid Token");
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