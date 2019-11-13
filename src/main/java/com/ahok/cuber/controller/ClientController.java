package com.ahok.cuber.controller;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.ahok.cuber.entity.Client;
import com.ahok.cuber.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GET
    @RequestMapping("clients/all")
    @Produces("application/json")
    public Response getAllClients() {
        List<Client> clientsList =
                clientService
                        .getAllClients();
        return Response.ok(clientsList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @RequestMapping("clients/get/{id}")
    @Produces("application/json")
    public Response getClient(@PathVariable("id") String clientId) {
        return Response.ok(clientService.getClient(clientId), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @RequestMapping("clients/add")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createClient(@RequestBody @Validated Client client) {
        return Response.ok(clientService.createClient(client), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @RequestMapping("clients/update")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateClient(@RequestBody @Validated Client client) {
        return Response.ok(clientService.updateClient(client), MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @RequestMapping("clients/delete/{id}")
    public Response deleteClient(@PathVariable("id") String clientId) {
        return Response.ok(clientService.deleteClient(clientId), MediaType.APPLICATION_JSON).build();
    }
}
