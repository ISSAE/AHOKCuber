package com.ahok.cuber.socket.service;

import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.service.DriverService;
import com.ahok.cuber.service.TripService;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ExceptionListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class SocketService {
    private SocketIOServer server;

    @Autowired
    private DriverService driverService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TripService tripService;

    public SocketService() {
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(9092);
        config.setExceptionListener(new ExceptionListener() {
            @Override
            public void onEventException(Exception e, List<Object> list, SocketIOClient socketIOClient) {

            }

            @Override
            public void onDisconnectException(Exception e, SocketIOClient socketIOClient) {

            }

            @Override
            public void onConnectException(Exception e, SocketIOClient socketIOClient) {
                System.out.println("connection exception");
                System.out.println(e.getMessage());

            }

            @Override
            public void onPingException(Exception e, SocketIOClient socketIOClient) {

            }

            @Override
            public boolean exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
                return true;
            }
        });
        this.server = new SocketIOServer(config);
        this.server.start();
    }

    public SocketIOServer getServer() {
        return this.server;
    }

    public DriverService getDriverService() {
        return driverService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public TripService getTripService() {
        return tripService;
    }

    public <T> void broadCastToRoom(T object, String roomName, String event) {
        this.getServer().getNamespace("/driver").getRoomOperations(roomName).sendEvent("trip_updated", object);
        this.getServer().getNamespace("/client").getRoomOperations(roomName).sendEvent("trip_updated", object);
    }
}
