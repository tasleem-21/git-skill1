package com.inventory.dao;



import org.hibernate.Session;
import org.hibernate.Transaction;
import com.klu.entity.Product;
import com.klu.util.HibernateUtil;

public class ProductDAO {

    public void saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(product);
        tx.commit();
        session.close();
    }

    public Product getProduct(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = session.get(Product.class, id);
        session.close();
        return product;
    }

    public void updateProduct(int id, double price, int quantity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Product product = session.get(Product.class, id);
        product.setPrice(price);
        product.setQuantity(quantity);
        tx.commit();
        session.close();
    }

    public void deleteProduct(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.delete(product);
        tx.commit();
        session.close();
    }
}