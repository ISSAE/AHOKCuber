package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Client;
import com.ahok.cuber.service.ClientService;
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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "clients/all",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getAllClients() {
        List<Client> clientsList =
                clientService
                        .getAllClients();
        return Response.ok(clientsList);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "clients/get/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getClient(@PathVariable("id") String clientId) {
        return Response.ok(clientService.getClient(clientId));
    }

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "clients/delete/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteClient(@PathVariable("id") String clientId) {
        return Response.ok(clientService.deleteClient(clientId));
    }
}
