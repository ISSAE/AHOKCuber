package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Client;
import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.socket.SocketService;
import com.ahok.cuber.util.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private SocketService socketService;

    @RequestMapping(value = "clients/all",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getAllClients() {
        List<Client> clientsList =
                clientService
                        .getAllClients();
        return Response.ok(clientsList);
    }

    @RequestMapping(value = "clients/get/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getClient(@PathVariable("id") String clientId) {
        return Response.ok(clientService.getClient(clientId));
    }

    @RequestMapping(value = "sockets/get",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getSocket() {
        return Response.ok(socketService.getServer().getConfiguration().getHostname() + ":" + socketService.getServer().getConfiguration().getPort());
    }

    @RequestMapping(value = "clients/add",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity createClient(@RequestBody @Validated Client client) {
        String action = clientService.createClient(client);
        if (action == null) {
            return Response.badRequest(String.format("Client with email (%s) already exists", client.getEmail()));
        }
        return Response.ok(client);
    }

    @RequestMapping(value = "clients/update",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.PUT)
    public ResponseEntity updateClient(@RequestBody @Validated Client client) {
        // TODO
        // handle IllegalArgumentException if `id` is missing.
        // handle NullPointerException if client with given `id` not found
        return Response.ok(clientService.updateClient(client));
    }

    @RequestMapping(value = "clients/delete/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteClient(@PathVariable("id") String clientId) {
        return Response.ok(clientService.deleteClient(clientId));
    }
}
