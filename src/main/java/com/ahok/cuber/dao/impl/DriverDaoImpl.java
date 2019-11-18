package com.ahok.cuber.dao.impl;

import com.ahok.cuber.dao.DriverDao;
import com.ahok.cuber.entity.Driver;
import com.ahok.cuber.util.HibernateHelper;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class DriverDaoImpl implements DriverDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Driver> getAll() {
        return HibernateHelper.list(sessionFactory, Driver.class);
    }

    @Transactional
    public String insertNew(Driver driver) {
        if (HibernateHelper.isUserExists(sessionFactory, driver.getEmail(), Driver.class)) {
            return null;
        }
        return (String) sessionFactory.getCurrentSession().save(driver);
    }

    @Transactional
    public Driver get(String driverId) {
        return sessionFactory.getCurrentSession().get(Driver.class, driverId);
    }

    @Transactional
    public String update(Driver driver) {
        sessionFactory.getCurrentSession().update(driver);
        return "Driver information updated successfully";
    }

    @Transactional
    public String remove(String driverId) {
        Driver driver = sessionFactory.getCurrentSession().get(Driver.class, driverId);
        sessionFactory.getCurrentSession().delete(driver);
        return "Driver with id " + driver.getId() + " deleted successfully";
    }

    public Driver getAuth(String email, String password) {
        return HibernateHelper.getAuth(sessionFactory, email, password, Driver.class);
    }
}
