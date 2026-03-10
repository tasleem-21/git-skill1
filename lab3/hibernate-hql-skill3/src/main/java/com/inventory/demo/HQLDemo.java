package com.inventory.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.inventory.entity.Product;
import com.inventory.loader.ProductDataLoader;
import com.inventory.util.HibernateUtil;

public class HQLDemo {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {
            ProductDataLoader.loadSampleProducts(session);

            sortProductsByPriceAscending(session);
            sortProductsByPriceDescending(session);
            sortProductsByQuantityDescending(session);

            getFirstThreeProducts(session);
            getNextThreeProducts(session);

            countTotalProducts(session);
            countProductsInStock(session);
            countProductsByDescription(session);
            findMinMaxPrice(session);

            groupProductsByDescription(session);
            groupProductsWithAggregation(session);

            filterProductsByPriceRange(session, 20.0, 100.0);

            findProductsStartingWith(session, "D");
            findProductsEndingWith(session, "p");
            findProductsContaining(session, "Desk");
            findProductsByNameLength(session, 5);

        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }

    public static void sortProductsByPriceAscending(Session session) {
        Query<Product> query = session.createQuery("FROM Product p ORDER BY p.price ASC", Product.class);
        System.out.println("\n=== Products Sorted by Price (Ascending) ===");
        query.list().forEach(System.out::println);
    }

    public static void sortProductsByPriceDescending(Session session) {
        Query<Product> query = session.createQuery("FROM Product p ORDER BY p.price DESC", Product.class);
        System.out.println("\n=== Products Sorted by Price (Descending) ===");
        query.list().forEach(System.out::println);
    }

    public static void sortProductsByQuantityDescending(Session session) {
        Query<Product> query = session.createQuery("FROM Product p ORDER BY p.quantity DESC", Product.class);
        System.out.println("\n=== Products Sorted by Quantity (Highest First) ===");
        for (Product p : query.list()) {
            System.out.println(p.getName() + " - Quantity: " + p.getQuantity());
        }
    }

    public static void getFirstThreeProducts(Session session) {
        Query<Product> query = session.createQuery("FROM Product p ORDER BY p.id", Product.class);
        query.setFirstResult(0);
        query.setMaxResults(3);
        System.out.println("\n=== First 3 Products ===");
        query.list().forEach(System.out::println);
    }

    public static void getNextThreeProducts(Session session) {
        Query<Product> query = session.createQuery("FROM Product p ORDER BY p.id", Product.class);
        query.setFirstResult(3);
        query.setMaxResults(3);
        System.out.println("\n=== Next 3 Products ===");
        query.list().forEach(System.out::println);
    }

    public static void countTotalProducts(Session session) {
        Query<Long> query = session.createQuery("SELECT COUNT(p) FROM Product p", Long.class);
        System.out.println("\n=== Total Number of Products ===");
        System.out.println("Total Products: " + query.uniqueResult());
    }

    public static void countProductsInStock(Session session) {
        Query<Long> query = session.createQuery("SELECT COUNT(p) FROM Product p WHERE p.quantity > 0", Long.class);
        System.out.println("\n=== Products in Stock (Quantity > 0) ===");
        System.out.println("Products in Stock: " + query.uniqueResult());
    }

    public static void countProductsByDescription(Session session) {
        Query<Object[]> query = session.createQuery(
                "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",
                Object[].class);
        System.out.println("\n=== Products Grouped by Description ===");
        for (Object[] row : query.list()) {
            System.out.println(row[0] + ": " + row[1] + " products");
        }
    }

    public static void findMinMaxPrice(Session session) {
        Query<Object[]> query = session.createQuery(
                "SELECT MIN(p.price), MAX(p.price) FROM Product p",
                Object[].class);
        Object[] row = query.uniqueResult();
        System.out.println("\n=== Price Range ===");
        System.out.println("Minimum Price: $" + row[0]);
        System.out.println("Maximum Price: $" + row[1]);
    }

    public static void groupProductsByDescription(Session session) {
        Query<Object[]> query = session.createQuery(
                "SELECT p.description, p.name, p.price FROM Product p ORDER BY p.description, p.name",
                Object[].class);

        System.out.println("\n=== Products Grouped by Description ===");
        String current = null;
        for (Object[] row : query.list()) {
            String desc = (String) row[0];
            String name = (String) row[1];
            Double price = (Double) row[2];

            if (current == null || !desc.equals(current)) {
                System.out.println("\n" + desc + ":");
                current = desc;
            }
            System.out.println("  - " + name + " ($" + price + ")");
        }
    }

    public static void groupProductsWithAggregation(Session session) {
        Query<Object[]> query = session.createQuery(
                "SELECT p.description, COUNT(p), AVG(p.price), SUM(p.quantity) " +
                "FROM Product p GROUP BY p.description",
                Object[].class);

        System.out.println("\n=== Product Statistics by Category ===");
        for (Object[] row : query.list()) {
            System.out.println(row[0] + ":");
            System.out.println("  Count: " + row[1]);
            System.out.println("  Avg Price: $" + String.format("%.2f", (Double) row[2]));
            System.out.println("  Total Quantity: " + row[3]);
        }
    }

    public static void filterProductsByPriceRange(Session session, double minPrice, double maxPrice) {
        Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.price",
                Product.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);

        System.out.println("\n=== Products Between $" + minPrice + " and $" + maxPrice + " ===");
        for (Product p : query.list()) {
            System.out.println(p.getName() + " - $" + p.getPrice());
        }
    }

    public static void findProductsStartingWith(Session session, String prefix) {
        Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern ORDER BY p.name",
                Product.class);
        query.setParameter("pattern", prefix + "%");

        System.out.println("\n=== Products Starting with '" + prefix + "' ===");
        query.list().forEach(p -> System.out.println(p.getName()));
    }

    public static void findProductsEndingWith(Session session, String suffix) {
        Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern ORDER BY p.name",
                Product.class);
        query.setParameter("pattern", "%" + suffix);

        System.out.println("\n=== Products Ending with '" + suffix + "' ===");
        query.list().forEach(p -> System.out.println(p.getName()));
    }

    public static void findProductsContaining(Session session, String substring) {
        Query<Product> query = session.createQuery(
                "FROM Product p WHERE p.name LIKE :pattern ORDER BY p.name",
                Product.class);
        query.setParameter("pattern", "%" + substring + "%");

        System.out.println("\n=== Products Containing '" + substring + "' ===");
        query.list().forEach(p -> System.out.println(p.getName()));
    }

    public static void findProductsByNameLength(Session session, int length) {
        Query<Product> query = session.createQuery(
                "FROM Product p WHERE LENGTH(p.name) = :length ORDER BY p.name",
                Product.class);
        query.setParameter("length", length);

        System.out.println("\n=== Products with Name Length " + length + " ===");
        for (Product p : query.list()) {
            System.out.println(p.getName() + " (Length: " + p.getName().length() + ")");
        }
    }
}