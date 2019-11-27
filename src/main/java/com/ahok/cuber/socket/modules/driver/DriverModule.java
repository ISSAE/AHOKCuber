package com.ahok.cuber.socket.modules.driver;

import com.ahok.cuber.entity.Trip;
import com.ahok.cuber.pojo.DriverPojo;
import com.ahok.cuber.pojo.TripPojo;
import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.service.DriverService;
import com.ahok.cuber.service.TripService;
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

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class DriverModule {
    private final SocketIONamespace namespace;

    private final SocketIOServer server;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TripService tripService;

    @Autowired
    public DriverModule(SocketService socketService) {
        this.server = socketService.getServer();
        this.namespace = server.addNamespace("/driver");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("get_location", UserLocation.class, onLocationReceived());
        this.namespace.addEventListener("trip_accepted", String.class, onTripAccepted());
        this.namespace.addEventListener("trip_declined", String.class, onTripDeclined());
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
                    System.out.println("Can't send location to unknown client");
                }
            }
        };
    }

    private DataListener<String> onTripAccepted() {
        return (driver, clientID, ackSender) -> {
            SocketIOClient client = SocketUtil.get(this.server.getNamespace("/client"), clientID);
            System.out.printf("Driver[%s] - Accepted Client request '%s'\n", driver.getSessionId().toString(), clientID);
            if (client != null) {
                SocketUser user = SocketUtil.auth(driver);
                if (user != null) {
                    Trip trip = new Trip();
                    trip.setClient(clientService.getClient(clientID));
                    trip.setDriver(driverService.getDriver(user.getUserID()));
                    trip.setStarted_at(new Date());
                    trip.setStatus(Trip.Status.CLIENT_WAITING);

                    tripService.createTrip(trip);
                    driver.joinRoom(trip.getId());
                    client.joinRoom(trip.getId());

                    client.sendEvent("trip_accepted", new TripPojo(trip));
                } else {
                    System.out.println("Can't accept trip request, client not found!");
                }
            }
        };
    }

    private DataListener<String> onTripDeclined() {
        return (driver, clientID, ackSender) -> {
            SocketIOClient client = SocketUtil.get(this.server.getNamespace("/client"), clientID);
            System.out.printf("Driver[%s] - Declined Client request '%s'\n", driver.getSessionId().toString(), clientID);
            if (client != null) {
                SocketUser user = SocketUtil.auth(driver);
                if (user != null) {
                    client.sendEvent("trip_declined", new DriverPacket(new DriverPojo(driverService.getDriver(user.getUserID())), ""));
                } else {
                    System.out.println("Can't decline Trip Request, client not found!");
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