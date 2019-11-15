package com.ahok.cuber.dao.impl;

import com.ahok.cuber.dao.DriverDao;
import com.ahok.cuber.entity.Driver;
import com.ahok.cuber.util.HibernateHelper;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DriverDaoImpl implements DriverDao {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Driver> getAll() {
        return (List<Driver>) sessionFactory.getCurrentSession().createCriteria(Driver.class).list();
    }

    @Transactional
    public String insertNew(Driver driver) {
        // insert into database & return primary key
        return (String) sessionFactory.getCurrentSession().save(driver);
    }

    @Transactional
    public Driver get(String driverId) {
        return (Driver) sessionFactory.getCurrentSession().get(Driver.class, driverId);
    }

    @Transactional
    public String update(Driver driver) {
        sessionFactory.getCurrentSession().update(driver);
        return "Driver information updated successfully";
    }

    @Transactional
    public String remove(String driverId) {
        Driver driver = (Driver) sessionFactory.getCurrentSession().get(Driver.class, driverId);
        sessionFactory.getCurrentSession().delete(driver);
        return "Driver information with id " + driver.getId() + " deleted successfully";
    }

    public Driver getAuth(String email, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery( "FROM Driver WHERE email=:email AND password=:password" )
                .setParameter("email", email)
                .setParameter("password", password);

        return HibernateHelper.getFirst(query);
    }
}
