package com.ahok.cuber.dao.impl;

import java.util.List;

import com.ahok.cuber.dao.ClientDao;
import com.ahok.cuber.entity.Client;
import com.ahok.cuber.util.HibernateHelper;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Transactional
public class ClientDaoImpl implements ClientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Client> getAll() {
        return HibernateHelper.list(sessionFactory, Client.class);
    }

    @Transactional
    public String insertNew(Client user) {
        if (HibernateHelper.isUserExists(sessionFactory, user.getEmail(), Client.class)) {
            return null;
        }
        return (String) sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public Client get(String userId) {
        return sessionFactory.getCurrentSession().get(Client.class, userId);
    }

    @Transactional
    public String update(Client user) {
        sessionFactory.getCurrentSession().update(user);
        return "Client information updated successfully";
    }

    @Transactional
    public String remove(String userId) {
        Client client = sessionFactory.getCurrentSession().get(Client.class, userId);
        sessionFactory.getCurrentSession().delete(client);
        return "Client with id " + client.getId() + " deleted successfully";
    }

    public Client getAuth(String email, String password) {
        return HibernateHelper.getAuth(sessionFactory, email, password, Client.class);
    }
}
