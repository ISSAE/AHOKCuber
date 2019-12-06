package com.ahok.cuber.socket.modules;

import com.ahok.cuber.entity.Trip;
import com.ahok.cuber.pojo.ClientPojo;
import com.ahok.cuber.socket.pojo.ClientPacket;
import com.ahok.cuber.socket.pojo.UserLocation;
import com.ahok.cuber.socket.service.SocketService;
import com.ahok.cuber.socket.pojo.SocketUser;
import com.ahok.cuber.socket.pojo.TripRequest;
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

    private final SocketService socketService;

    @Autowired
    public ClientModule(SocketService socketService) {
        this.socketService = socketService;
        this.server = socketService.getServer();
        this.namespace = server.addNamespace("/client");
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("get_location", UserLocation.class, onLocationReceived());
        this.namespace.addEventListener("request_location", Object.class, onRequestLocation());
        this.namespace.addEventListener("request_trip", TripRequest.class, onRequestTrip());
        this.namespace.addEventListener("driver_arrived", String.class, onDriverArrived());
        this.namespace.addEventListener("could_not_meet", String.class, onCouldNotMeet());
        this.namespace.addEventListener("arrived_to_dest", String.class, onArrivedToDest());
    }

    private DataListener<UserLocation> onLocationReceived() {
        return (client, userLocation, ackSender) -> {
            SocketIOClient _driver = SocketUtil.get(this.server.getNamespace("/driver"), userLocation.getUser().getUserID());
            System.out.printf("Client[%s] - Received location data '%s'\n", client.getSessionId().toString(), userLocation);
            if (_driver != null) {
                SocketUser user = SocketUtil.auth(client);
                if (user != null) {
                    _driver.sendEvent("receive_location",
                            new ClientPacket(new ClientPojo(this.socketService.getClientService().getClient(user.getUserID())), userLocation.getLocation(), ""));
                } else {
                    System.out.println("Can't send location to unknown driver");
                }
            }
        };
    }

    private DataListener<Object> onRequestLocation() {
        return (client, data, ackSender) -> {
            SocketUser user = SocketUtil.auth(client);
            System.out.println(String.format("Client[%s] - Request Candidate Driver location.\n", client.getSessionId().toString()));
            this.server.getNamespace("/driver").getBroadcastOperations().sendEvent("request_location", user);
        };
    }

    private DataListener<TripRequest> onRequestTrip() {
        return (client, tripRequest, ackSender) -> {
            SocketUser user = SocketUtil.auth(client);
            System.out.println(String.format("Client[%s] - Request Trip from driver %s.\n", client.getSessionId().toString(), tripRequest.getDriver()));
            SocketIOClient driverSocket = SocketUtil.get(this.server.getNamespace("/driver"), tripRequest.getDriver());
            if (driverSocket != null) {
                System.out.println(tripRequest.getClientDestination());
                driverSocket.sendEvent("request_trip", new ClientPacket(
                        new ClientPojo(this.socketService.getClientService().getClient(user.getUserID())),
                                        tripRequest.getClientLocation(), tripRequest.getClientDestination()));
            }
        };
    }

    private DataListener<String> onDriverArrived() {
        return (client, tripID, ackSender) -> {
            Trip trip = this.socketService.getTripService().getTrip(tripID);
            trip.setStatus(Trip.Status.CLIENT_PICKED_UP);
            this.socketService.getTripService().updateTrip(trip);
        };
    }

    private DataListener<String> onCouldNotMeet() {
        return (client, tripID, ackSender) -> {
            Trip trip = this.socketService.getTripService().getTrip(tripID);
            trip.setStatus(Trip.Status.COULD_NOT_MEET);
            this.socketService.getTripService().updateTrip(trip);
        };
    }

    private DataListener<String> onArrivedToDest() {
        return (client, tripID, ackSender) -> {
            Trip trip = this.socketService.getTripService().getTrip(tripID);
            trip.setStatus(Trip.Status.FINISHED);
            this.socketService.getTripService().updateTrip(trip);
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