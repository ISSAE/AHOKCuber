package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Client;
import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.util.Config;
import com.ahok.cuber.util.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "auth/token",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity token(@RequestBody Client c) {
        String token;
        Client client = clientService.getAuth(c.getEmail(), c.getPassword());

        if (client == null) return ResponseEntity.badRequest().body(JSON.toJSON(String.format("Wrong username or password (%s / %s)", c.getEmail(), c.getPassword())));

        try {
            Algorithm algorithm = Algorithm.HMAC256(Config.getProperty("AUTH_PASSPHRASE"));
            token = JWT.create()
                    .withIssuer("cuber")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return ResponseEntity.status(500).body(JSON.toJSON(String.format("Can't create a token! Error Message: %s", exception.getMessage())));
        }

        return ResponseEntity.ok(JSON.toJSON(token));
    }

    @RequestMapping(value = "auth/client/register",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody Client client) {
        clientService.createClient(client);
        return ResponseEntity.ok(JSON.toJSON(client));
    }
}
