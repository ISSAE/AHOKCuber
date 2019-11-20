package com.ahok.cuber.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Component;

@Component
public class SocketService {
    private SocketIOServer server;

    public SocketService() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        this.server = new SocketIOServer(config);
        this.server.start();
    }

    public SocketIOServer getServer() {
        return this.server;
    }
}
