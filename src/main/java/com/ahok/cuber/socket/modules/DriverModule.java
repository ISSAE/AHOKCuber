package com.ahok.cuber.socket.modules;

import com.ahok.cuber.entity.Trip;
import com.ahok.cuber.pojo.DriverPojo;
import com.ahok.cuber.pojo.TripPojo;
import com.ahok.cuber.socket.pojo.DriverPacket;
import com.ahok.cuber.socket.service.SocketService;
import com.ahok.cuber.socket.pojo.SocketUser;
import com.ahok.cuber.socket.pojo.UserLocation;
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
import java.util.Timer;
import java.util.TimerTask;

@Component
public class DriverModule {
    private final SocketIONamespace namespace;

    private final SocketIOServer server;

    private final SocketService socketService;

    @Autowired
    public DriverModule(SocketService socketService) {
        this.socketService = socketService;
        this.server = socketService.getServer();
        this.namespace = server.addNamespace("/driver");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("get_location", UserLocation.class, onLocationReceived());
        this.namespace.addEventListener("trip_accepted", String.class, onTripAccepted());
        this.namespace.addEventListener("trip_declined", String.class, onTripDeclined());
        this.namespace.addEventListener("driver_arrived", String.class, onDriverArrived());
        this.namespace.addEventListener("could_not_meet", String.class, onCouldNotMeet());
        this.namespace.addEventListener("arrived_to_dest", String.class, onArrivedToDest());
    }

    private DataListener<UserLocation> onLocationReceived() {
        return (driver, userLocation, ackSender) -> {
            SocketIOClient client = SocketUtil.get(this.server.getNamespace("/client"), userLocation.getUser().getUserID());
            System.out.printf("Driver[%s] - Received location data '%s'\n", driver.getSessionId().toString(), userLocation);
            if (client != null) {
                SocketUser user = SocketUtil.auth(driver);
                if (user != null) {
                    client.sendEvent("receive_location", new DriverPacket(new DriverPojo(this.socketService.getDriverService().getDriver(user.getUserID())), userLocation.getLocation()));
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
                    trip.setClient(this.socketService.getClientService().getClient(clientID));
                    trip.setDriver(this.socketService.getDriverService().getDriver(user.getUserID()));
                    trip.setStarted_at(new Date());
                    trip.setStatus(Trip.Status.CLIENT_WAITING);

                    socketService.getTripService().createTrip(trip);

                    client.sendEvent("trip_accepted", new TripPojo(trip));

                    client.joinRoom(trip.getId());
                    driver.joinRoom(trip.getId());

                    this.startTripNotifier(trip);
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
                    client.sendEvent("trip_declined", new DriverPacket(new DriverPojo(this.socketService.getDriverService().getDriver(user.getUserID())), ""));
                } else {
                    System.out.println("Can't decline Trip Request, client not found!");
                }
            }
        };
    }

    private DataListener<String> onDriverArrived() {
        return (driver, tripID, ackSender) -> {
            Trip trip = socketService.getTripService().getTrip(tripID);
            trip.setStatus(Trip.Status.CLIENT_PICKED_UP);
            socketService.getTripService().updateTrip(trip);
        };
    }

    private DataListener<String> onCouldNotMeet() {
        return (driver, tripID, ackSender) -> {
            Trip trip = socketService.getTripService().getTrip(tripID);
            trip.setStatus(Trip.Status.COULD_NOT_MEET);
            socketService.getTripService().updateTrip(trip);
        };
    }

    private DataListener<String> onArrivedToDest() {
        return (driver, tripID, ackSender) -> {
            Trip trip = socketService.getTripService().getTrip(tripID);
            trip.setStatus(Trip.Status.FINISHED);
            socketService.getTripService().updateTrip(trip);
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

    private void startTripNotifier (Trip trip) {
        SocketIOClient client = SocketUtil.get(this.server.getNamespace("/client"), trip.getClient().getId());
        SocketIOClient driver = SocketUtil.get(this.server.getNamespace("/driver"), trip.getDriver().getId());

        final SocketUser cRO = SocketUtil.auth(Objects.requireNonNull(client)); // request object
        final SocketUser dRO = SocketUtil.auth(Objects.requireNonNull(driver));
        final String tripID = trip.getId();

        this.socketService.broadCastToRoom(new TripPojo(trip), tripID, "trip_updated");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Trip _trip = socketService.getTripService().getTrip(tripID);
                if (_trip.getStatus() == Trip.Status.COULD_NOT_MEET || _trip.getStatus() == Trip.Status.FINISHED || _trip.getStatus() == Trip.Status.CLIENT_PICKED_UP) {
                    t.cancel();
                }
                client.sendEvent("request_location", dRO);
                driver.sendEvent("request_location", cRO);
            }
        }, 0, 1000);
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