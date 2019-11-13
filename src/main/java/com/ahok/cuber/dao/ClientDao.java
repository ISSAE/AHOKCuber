package com.ahok.cuber.dao;

import com.ahok.cuber.entity.Client;

import java.util.List;

public interface ClientDao {
    List<Client> getAll();
    Client get(String userId);
    String insertNew(Client user);
    String update(Client user);
    String remove(String userId);
    Client getAuth(String email, String password);
}
