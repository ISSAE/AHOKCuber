package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Client;
import com.ahok.cuber.pojo.ClientPojo;
import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.util.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest CRUD Client Controller
 * <p>
 * Available endpoints:
 * <ul>
 * <li><b>GET</b>    clients/all            Get a list of all {@link Client}s</li>
 * <li><b>GET</b>    clients/get/{id}       Get {@link Client} by id</li>
 * <li><b>POST</b>   clients/add            Add new {@link Client}</li>
 * <li><b>PUT</b>    clients/update         Update {@link Client}</li>
 * <li><b>DELETE</b> clients/delete/{id}    Delete {@link Client}</li>
 * </ul>
 */
@RestController
public class ClientController {

    /**
     * Hibernate Service to manage Client Module
     */
    @Autowired
    private ClientService clientService;

    /**
     * Get All Clients
     * <p>
     * Method: GET
     *
     * @return clientsList ClientPojo
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "clients/all",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getAllClients() {
        List<Client> clientsList =
                clientService
                        .getAllClients();

        List<ClientPojo> clients = new ArrayList<>();
        clientsList.forEach(client -> clients.add(new ClientPojo(client)));

        return Response.ok(clients);
    }

    /**
     * Get Client by ID
     * <p>
     * Method: GET
     *
     * @param clientId PathVariable String
     * @return Client Client
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "clients/get/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.GET)
    public ResponseEntity getClient(@PathVariable("id") String clientId) {
        return Response.ok(new ClientPojo(clientService.getClient(clientId)));
    }

    /**
     * Create new Client
     * <p>
     * Method: POST
     *
     * @param client Client
     * @return client ClientPojo
     */
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
        return Response.ok(new ClientPojo(client));
    }

    /**
     * Update Client
     * <p>
     * Method: PUT
     *
     * @param client Client
     * @return message String
     */
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

    /**
     * Delete Client by id
     * <p>
     * Method: DELETE
     *
     * @param clientId PathVariable String
     * @return message String
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "clients/delete/{id}",
            produces = "application/json;charset=UTF-8",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteClient(@PathVariable("id") String clientId) {
        return Response.ok(clientService.deleteClient(clientId));
    }
}
