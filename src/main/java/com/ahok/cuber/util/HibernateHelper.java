package com.ahok.cuber.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.criterion.Order;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class HibernateHelper {

    public static <T> List<T> list(SessionFactory sessionFactory, Class<T> var1) {
        CriteriaQuery<T> cQuery = sessionFactory.getCriteriaBuilder().createQuery(var1);
        Root<T> root = cQuery.from(var1);
        cQuery.select(root);

        return sessionFactory.getCurrentSession().createQuery(cQuery).getResultList();
    }

    public static <T> List<T> list(final Query<T> query) {
        return query.list();
    }

    public static <T> T getAuth(SessionFactory sessionFactory, String email, String password, Class<T> var1) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery( var1 );
        Root<T> root = query.from( var1 );
        query.select(root).where(builder.and(
                builder.equal(root.get("email"), email),
                builder.equal(root.get("password"), password)
        ));

        try {
            return sessionFactory.getCurrentSession().createQuery( query ).getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    public static <T> boolean isUserExists(SessionFactory sessionFactory, String email, Class<T> var1) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery( var1 );
        Root<T> root = query.from( var1 );
        query.select(root).where(builder.and(
                builder.equal(root.get("email"), email)
        ));

        try {
            return sessionFactory.getCurrentSession().createQuery(query).getSingleResult() != null;
        } catch (NoResultException exception) {
            return false;
        }
    }

    public static <T> String toSQLList(List<T> list) {
        StringBuilder sb = new StringBuilder(list.size() * 10);
        for (T t : list) {
            sb.append(t).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static int firstResult(int currentPage, int pageSize) {
        final int offset = (currentPage - 1) * pageSize;

        if (offset < 0) {
            StringBuilder msg = new StringBuilder(100);
            msg.append(" offset: ").append(offset);
            msg.append(" currentPage: ").append(currentPage);
            msg.append(" pageSize: ").append(pageSize);
            throw new IllegalArgumentException(msg.toString());
        }
        return offset;
    }

    public static void addOrder(Criteria cri, String sort, String order) {
        if (order != null) {
            if (!"asc".equals(order) && !"desc".equals(order)) {
                throw new IllegalArgumentException(order);
            }
        }
        if (sort != null && !sort.isEmpty()) {
            if ("asc".equals(order)) {
                cri.addOrder(Order.asc(sort));
            } else {
                cri.addOrder(Order.desc(sort));
            }
        }
    }
}
