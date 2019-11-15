package com.ahok.cuber.service.impl;

import com.ahok.cuber.dao.ClientDao;
import com.ahok.cuber.entity.Client;
import com.ahok.cuber.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao clientDao;

    @Override
    public List<Client> getAllClients() {
        return clientDao.getAll();
    }

    @Override
    public Client getClient(String clientId) {
        return clientDao.get(clientId);
    }

    @Override
    public String createClient(Client client) {
        return clientDao.insertNew(client);
    }

    @Override
    public String updateClient(Client client) {
        return clientDao.update(client);
    }

    @Override
    public String deleteClient(String clientId) {
        return clientDao.remove(clientId);
    }

    @Override
    public Client getAuth(String email, String password) {
        return clientDao.getAuth(email, password);
    }
}
