package com.ahok.cuber.dao.impl;

import java.util.List;

import com.ahok.cuber.dao.ClientDao;
import com.ahok.cuber.entity.Client;
import com.ahok.cuber.util.HibernateHelper;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ClientDaoImpl implements ClientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Client> getAll() {
        return (List<Client>) sessionFactory.getCurrentSession().createCriteria(Client.class).list();
    }

    @Transactional
    public String insertNew(Client user) {
        // insert into database & return primary key
        return (String) sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public Client get(String userId) {
        return (Client) sessionFactory.getCurrentSession().get(Client.class, userId);
    }

    @Transactional
    public String update(Client user) {
        sessionFactory.getCurrentSession().update(user);
        return "Client information updated successfully";
    }

    @Transactional
    public String remove(String userId) {
        Client client = (Client) sessionFactory.getCurrentSession().get(Client.class, userId);
        sessionFactory.getCurrentSession().delete(client);
        return "Client information with id " + client.getId() + " deleted successfully";
    }

    public Client getAuth(String email, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery( "FROM Client WHERE email=:email AND password=:password" )
                .setParameter("email", email)
                .setParameter("password", password);

        return HibernateHelper.getFirst(query);
    }
}
