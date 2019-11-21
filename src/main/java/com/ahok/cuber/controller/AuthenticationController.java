package com.ahok.cuber.controller;

import com.ahok.cuber.controller.reqpojo.UserLogin;
import com.ahok.cuber.entity.Client;
import com.ahok.cuber.entity.Driver;
import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.service.DriverService;
import com.ahok.cuber.util.Config;
import com.ahok.cuber.util.http.Response;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DriverService driverService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "auth/token",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity token(@RequestBody UserLogin user) {
        Client client = clientService.getAuth(user.getEmail(), user.getPassword());

        if (client == null) return Response.badRequest("Wrong username or password");

        return generateToken(client.getId(), false);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "auth/driver/token",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity driverToken(@RequestBody UserLogin user) {
        Driver driver = driverService.getAuth(user.getEmail(), user.getPassword());

        if (driver == null) return Response.badRequest("Wrong username or password");

        return generateToken(driver.getId(), true);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "auth/client/register",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody Client client) {
        String action = clientService.createClient(client);
        if (action == null) {
            return Response.badRequest(String.format("Client with email (%s) already exists", client.getEmail()));
        }
        clientService.createClient(client);
        return generateToken(client.getId(), false);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "auth/driver/register",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity driverRegister(@RequestBody Driver driver) {
        String action = driverService.createDriver(driver);
        if (action == null) {
            return Response.badRequest(String.format("Driver with email (%s) already exists", driver.getEmail()));
        }
        driverService.createDriver(driver);
        return generateToken(driver.getId(), true);
    }

    private ResponseEntity generateToken(String id, boolean isDriver) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Config.getProperty("AUTH_PASSPHRASE"));
            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("userID", id);
            headerClaims.put("isDriver", isDriver);
            return Response.ok(JWT.create()
                    .withHeader(headerClaims)
                    .withIssuer("cuber")
                    .sign(algorithm));
        } catch (JWTCreationException exception) {
            return Response.internalError(String.format("Can't create a token! Error Message: %s", exception.getMessage()));
        }
    }
}
