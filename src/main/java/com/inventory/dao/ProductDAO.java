package com.inventory.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.inventory.util.HibernateUtil;

public class ProductDAO {

    // CREATE
    public <T> Integer save(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // save() returns generated id for many strategies
        Integer id = (Integer) session.save(entity);

        tx.commit();
        session.close();
        return id;
    }

    // READ by id
    public <T> T getById(Class<T> clazz, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T entity = session.get(clazz, id);
        session.close();
        return entity;
    }

    // UPDATE: load -> modify in calling code -> merge
    public <T> boolean update(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.merge(entity);

        tx.commit();
        session.close();
        return true;
    }

    // DELETE by id
    public <T> boolean deleteById(Class<T> clazz, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        T entity = session.get(clazz, id);
        if (entity == null) {
            tx.rollback();
            session.close();
            return false;
        }

        session.remove(entity);

        tx.commit();
        session.close();
        return true;
    }
}