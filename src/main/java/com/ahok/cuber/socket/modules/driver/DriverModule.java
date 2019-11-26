package com.ahok.cuber.socket.modules.driver;

import com.ahok.cuber.pojo.DriverPojo;
import com.ahok.cuber.service.DriverService;
import com.ahok.cuber.socket.SocketService;
import com.ahok.cuber.socket.modules.SocketUser;
import com.ahok.cuber.socket.modules.UserLocation;
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
public class DriverModule {
    private final SocketIONamespace namespace;

    private final SocketIOServer server;

    @Autowired
    private DriverService driverService;

    @Autowired
    public DriverModule(SocketService socketService) {
        this.server = socketService.getServer();
        this.namespace = server.addNamespace("/driver");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("get_location", UserLocation.class, onLocationReceived());
    }

    private DataListener<UserLocation> onLocationReceived() {
        return (driver, userLocation, ackSender) -> {
            SocketIOClient client = SocketUtil.get(this.server.getNamespace("/client"), userLocation.getUser().getUserID());
            System.out.printf("Driver[%s] - Received location data '%s'\n", driver.getSessionId().toString(), userLocation);
            if (client != null) {
                SocketUser user = SocketUtil.auth(driver);
                if (user != null) {
                    client.sendEvent("receive_location", new DriverPacket(new DriverPojo(driverService.getDriver(user.getUserID())), userLocation.getLocation()));
                } else {
                    System.out.println("Can't sent location to client from unknown client");
                }
            }
        };
    }

    private ConnectListener onConnected() {
        return driver -> {
            String token = driver.getHandshakeData().getSingleUrlParam("token");
            SocketUser user = SocketUtil.auth(driver);
            if (user != null) {
                if (!user.isDriver()) {
                    disconnect(driver, "Client cannot login to driver module");
                    return;
                }
                driver.set("ownerID", user.getUserID());

                System.out.printf("Driver[%s] - Connected to driver module with token => '%s'\n", driver.getSessionId().toString(), token);
                System.out.printf("Number of connected drivers '%s'\n", namespace.getAllClients().size());
            } else {
                disconnect(driver, "Invalid Token");
            }
        };
    }

    private void disconnect (SocketIOClient client, String message) {
        client.sendEvent("invalid_token");
        System.out.println(message);
        client.disconnect();
    }

    private DisconnectListener onDisconnected() {
        return client -> System.out.printf("Driver[%s] - Disconnected from driver module.\n", client.getSessionId().toString());
    }
}