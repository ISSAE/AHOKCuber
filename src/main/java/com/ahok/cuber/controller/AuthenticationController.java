package com.ahok.cuber.controller;

import com.ahok.cuber.entity.Client;
import com.ahok.cuber.service.ClientService;
import com.ahok.cuber.util.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "auth/token",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity token(@RequestParam String username,
                                @RequestParam String password) {
        String token;
        Client client = clientService.getAuth(username, password);

        if (client == null) return ResponseEntity.badRequest().body("Wrong username or password");

        try {
            Algorithm algorithm = Algorithm.HMAC256(Config.getProperty("AUTH_PASSPHRASE"));
            token = JWT.create()
                    .withIssuer("cuber")
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return ResponseEntity.status(500).body(String.format("Can't create a token! Error Message: %s", exception.getMessage()));
        }

        return ResponseEntity.ok(token);
    }

    @RequestMapping(value = "auth/client/register",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity register(Client client) {
        clientService.createClient(client);
        return ResponseEntity.ok(client.toString());
    }
}
