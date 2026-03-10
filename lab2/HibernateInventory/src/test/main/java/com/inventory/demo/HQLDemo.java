package com.inventory.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.inventory.entity.skill3.Product;
import com.inventory.loader.ProductDataLoader;
import com.inventory.util.HibernateUtil;

public class HQLDemo {

    public static void main(String[] args) {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {
            // Insert sample data (only if table empty)
            ProductDataLoader.loadSampleProducts(session);

            // Task 3: sorting by price
            sortProductsByPriceAscending(session);
            sortProductsByPriceDescending(session);

            // Task 4: sorting by quantity
            sortProductsByQuantityDescending(session);

            // Task 5: pagination
            getFirstThreeProducts(session);
            getNextThreeProducts(session);
            getPaginatedProducts(session, 3, 3);

            // Task 6: aggregates
            countTotalProducts(session);
            countProductsInStock(session);
            countProductsByDescription(session);
            findMinMaxPrice(session);

            // Task 7: group by
            groupProductsByDescription(session);
            groupProductsWithAggregation(session);

            // Task 8: filter by price range
            filterProductsByPriceRange(session, 20.0, 100.0);

            // Task 9: LIKE queries
            findProductsStartingWith(session, "D");
            findProductsEndingWith(session, "p");
            findProductsContaining(session, "Desk");
            findProductsByNameLength(session, 5);

        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }

    // Task 3a: Sort by price (ASC)
    public static void sortProductsByPriceAscending(Session session) {
        String hql = "FROM Product p ORDER BY p.price ASC";
        Query<Product> query = session.createQuery(hql, Product.class);
        List<Product> products = query.list();

        System.out.println("\n=== Products Sorted by Price (Ascending) ===");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    // Task 3b: Sort by price (DESC)
    public static void sortProductsByPriceDescending(Session session) {
        String hql = "FROM Product p ORDER BY p.price DESC";
        Query<Product> query = session.createQuery(hql, Product.class);
        List<Product> products = query.list();

        System.out.println("\n=== Products Sorted by Price (Descending) ===");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    // Task 4: Sort by quantity (DESC)
    public static void sortProductsByQuantityDescending(Session session) {
        String hql = "FROM Product p ORDER BY p.quantity DESC";
        Query<Product> query = session.createQuery(hql, Product.class);
        List<Product> products = query.list();

        System.out.println("\n=== Products Sorted by Quantity (Highest First) ===");
        for (Product p : products) {
            System.out.println(p.getName() + " - Quantity: " + p.getQuantity());
        }
    }

    // Task 5a: First 3 products
    public static void getFirstThreeProducts(Session session) {
        String hql = "FROM Product p ORDER BY p.id";
        Query<Product> query = session.createQuery(hql, Product.class);
        query.setFirstResult(0);
        query.setMaxResults(3);

        System.out.println("\n=== First 3 Products ===");
        for (Product p : query.list()) {
            System.out.println(p);
        }
    }

    // Task 5b: Next 3 products
    public static void getNextThreeProducts(Session session) {
        String hql = "FROM Product p ORDER BY p.id";
        Query<Product> query = session.createQuery(hql, Product.class);
        query.setFirstResult(3);
        query.setMaxResults(3);

        System.out.println("\n=== Next 3 Products ===");
        for (Product p : query.list()) {
            System.out.println(p);
        }
    }

    // Generic pagination
    public static void getPaginatedProducts(Session session, int pageNumber, int pageSize) {
        String hql = "FROM Product p ORDER BY p.id";
        Query<Product> query = session.createQuery(hql, Product.class);

        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);

        System.out.println("\n=== Page " + pageNumber + " (Size: " + pageSize + ") ===");
        for (Product p : query.list()) {
            System.out.println(p);
        }
    }

    // Task 6a: Total count
    public static void countTotalProducts(Session session) {
        String hql = "SELECT COUNT(p) FROM Product p";
        Query<Long> query = session.createQuery(hql, Long.class);

        System.out.println("\n=== Total Number of Products ===");
        System.out.println("Total Products: " + query.uniqueResult());
    }

    // Task 6b: Count products in stock
    public static void countProductsInStock(Session session) {
        String hql = "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0";
        Query<Long> query = session.createQuery(hql, Long.class);

        System.out.println("\n=== Products in Stock (Quantity > 0) ===");
        System.out.println("Products in Stock: " + query.uniqueResult());
    }

    // Task 6c: Count grouped by description
    public static void countProductsByDescription(Session session) {
        String hql = "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description";
        Query<Object[]> query = session.createQuery(hql, Object[].class);

        System.out.println("\n=== Products Grouped by Description ===");
        for (Object[] row : query.list()) {
            String description = (String) row[0];
            Long count = (Long) row[1];
            System.out.println(description + ": " + count + " products");
        }
    }

    // Task 6d: Min/Max price
    public static void findMinMaxPrice(Session session) {
        String hql = "SELECT MIN(p.price), MAX(p.price) FROM Product p";
        Query<Object[]> query = session.createQuery(hql, Object[].class);

        Object[] row = query.uniqueResult();
        Double minPrice = (Double) row[0];
        Double maxPrice = (Double) row[1];

        System.out.println("\n=== Price Range ===");
        System.out.println("Minimum Price: $" + minPrice);
        System.out.println("Maximum Price: $" + maxPrice);
    }

    // Task 7: Group products by description (print items)
    public static void groupProductsByDescription(Session session) {
        String hql = "SELECT p.description, p.name, p.price FROM Product p ORDER BY p.description";
        Query<Object[]> query = session.createQuery(hql, Object[].class);

        System.out.println("\n=== Products Grouped by Description ===");
        String currentDescription = "";

        for (Object[] row : query.list()) {
            String description = (String) row[0];
            String name = (String) row[1];
            Double price = (Double) row[2];

            if (!description.equals(currentDescription)) {
                System.out.println("\n" + description + ":");
                currentDescription = description;
            }
            System.out.println("  - " + name + " ($" + price + ")");
        }
    }

    // Task 7 alternative: Aggregation by description
    public static void groupProductsWithAggregation(Session session) {
        String hql = "SELECT p.description, COUNT(p), AVG(p.price), SUM(p.quantity) " +
                     "FROM Product p GROUP BY p.description";
        Query<Object[]> query = session.createQuery(hql, Object[].class);

        System.out.println("\n=== Product Statistics by Category ===");
        for (Object[] row : query.list()) {
            String description = (String) row[0];
            Long count = (Long) row[1];
            Double avgPrice = (Double) row[2];
            Long totalQty = (Long) row[3];

            System.out.println(description + ":");
            System.out.println("  Count: " + count);
            System.out.println("  Avg Price: $" + String.format("%.2f", avgPrice));
            System.out.println("  Total Quantity: " + totalQty);
        }
    }

    // Task 8: Filter by price range
    public static void filterProductsByPriceRange(Session session, double minPrice, double maxPrice) {
        String hql = "FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice";
        Query<Product> query = session.createQuery(hql, Product.class);

        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);

        System.out.println("\n=== Products Between $" + minPrice + " and $" + maxPrice + " ===");
        for (Product p : query.list()) {
            System.out.println(p.getName() + " - $" + p.getPrice());
        }
    }

    // Task 9a: Names starting with prefix
    public static void findProductsStartingWith(Session session, String prefix) {
        String hql = "FROM Product p WHERE p.name LIKE :pattern";
        Query<Product> query = session.createQuery(hql, Product.class);

        query.setParameter("pattern", prefix + "%");

        System.out.println("\n=== Products Starting with '" + prefix + "' ===");
        for (Product p : query.list()) {
            System.out.println(p.getName());
        }
    }

    // Task 9b: Names ending with suffix
    public static void findProductsEndingWith(Session session, String suffix) {
        String hql = "FROM Product p WHERE p.name LIKE :pattern";
        Query<Product> query = session.createQuery(hql, Product.class);

        query.setParameter("pattern", "%" + suffix);

        System.out.println("\n=== Products Ending with '" + suffix + "' ===");
        for (Product p : query.list()) {
            System.out.println(p.getName());
        }
    }

    // Task 9c: Names containing substring
    public static void findProductsContaining(Session session, String substring) {
        String hql = "FROM Product p WHERE p.name LIKE :pattern";
        Query<Product> query = session.createQuery(hql, Product.class);

        query.setParameter("pattern", "%" + substring + "%");

        System.out.println("\n=== Products Containing '" + substring + "' ===");
        for (Product p : query.list()) {
            System.out.println(p.getName());
        }
    }

    // Task 9d: Names of exact length
    public static void findProductsByNameLength(Session session, int length) {
        String hql = "FROM Product p WHERE LENGTH(p.name) = :length";
        Query<Product> query = session.createQuery(hql, Product.class);

        query.setParameter("length", length);

        System.out.println("\n=== Products with Name Length " + length + " ===");
        for (Product p : query.list()) {
            System.out.println(p.getName() + " (Length: " + p.getName().length() + ")");
        }
    }
}