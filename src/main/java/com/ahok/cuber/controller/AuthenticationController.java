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

@RestController
public class AuthenticationController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DriverService driverService;

    @RequestMapping(value = "auth/token",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity token(@RequestBody UserLogin user) {
        Client client = clientService.getAuth(user.getEmail(), user.getPassword());

        if (client == null) return Response.badRequest("Wrong username or password");

        return generateToken();
    }

    @RequestMapping(value = "auth/driver/token",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity driverToken(@RequestBody UserLogin user) {
        Driver driver = driverService.getAuth(user.getEmail(), user.getPassword());

        if (driver == null) return Response.badRequest("Wrong username or password");

        return generateToken();
    }

    @RequestMapping(value = "auth/client/register",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody Client client) {
        clientService.createClient(client);
        return generateToken();
    }

    @RequestMapping(value = "auth/driver/register",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity driverRegister(@RequestBody Driver driver) {
        driverService.createDriver(driver);
        return generateToken();
    }

    private ResponseEntity generateToken() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Config.getProperty("AUTH_PASSPHRASE"));
            return Response.ok(JWT.create()
                    .withIssuer("cuber")
                    .sign(algorithm));
        } catch (JWTCreationException exception) {
            return Response.internalError(String.format("Can't create a token! Error Message: %s", exception.getMessage()));
        }
    }
}
