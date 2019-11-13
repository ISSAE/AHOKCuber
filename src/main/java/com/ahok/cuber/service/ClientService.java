package com.ahok.cuber.service;

import com.ahok.cuber.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client getClient(String clientId);

    String createClient(Client client);

    String updateClient(Client client);

    String deleteClient(String clientId);

    Client getAuth(String email, String password);
}
