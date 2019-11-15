package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Client;
import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.util.http.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "clients/all",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getAllClients() {
        List<Client> clientsList =
                clientService
                        .getAllClients();
        return ResponseEntity.ok(JSON.toJSON(clientsList));
    }

    @RequestMapping(value = "clients/get/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getClient(@PathVariable("id") String clientId) {
        return ResponseEntity.ok(JSON.toJSON(clientService.getClient(clientId)));
    }

    @RequestMapping(value = "clients/add",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity createClient(@RequestBody @Validated Client client) {
        return ResponseEntity.ok(JSON.toJSON(clientService.createClient(client)));
    }

    @RequestMapping(value = "clients/update",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.PUT)
    public ResponseEntity updateClient(@RequestBody @Validated Client client) {
        return ResponseEntity.ok(JSON.toJSON(clientService.updateClient(client)));
    }

    @RequestMapping(value = "clients/delete/{id}",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteClient(@PathVariable("id") String clientId) {
        return ResponseEntity.ok(JSON.toJSON(clientService.deleteClient(clientId)));
    }
}
