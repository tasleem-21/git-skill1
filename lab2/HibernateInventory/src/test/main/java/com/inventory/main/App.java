package com.inventory.main;

import com.inventory.dao.ProductDAO;
import com.inventory.entity.ProductAuto;
import com.inventory.entity.ProductIdentity;
import com.inventory.entity.ProductSequence;
import com.inventory.util.HibernateUtil;

public class App {
    public static void main(String[] args) {

        ProductDAO dao = new ProductDAO();

        try {
            // ========= AUTO =========
            ProductAuto a1 = new ProductAuto("Laptop", "AUTO - Gaming Laptop", 75000, 10);
            ProductAuto a2 = new ProductAuto("Mouse", "AUTO - Wireless Mouse", 1200, 50);

            int autoId1 = dao.save(a1);
            int autoId2 = dao.save(a2);

            System.out.println("AUTO inserted IDs: " + autoId1 + ", " + autoId2);
            System.out.println("AUTO get by id=" + autoId1 + ": " + dao.getById(ProductAuto.class, autoId1));

            // Update AUTO record (price/quantity)
            ProductAuto autoToUpdate = dao.getById(ProductAuto.class, autoId1);
            if (autoToUpdate != null) {
                autoToUpdate.setPrice(72000);
                autoToUpdate.setQuantity(8);
                dao.update(autoToUpdate);
                System.out.println("AUTO after update: " + dao.getById(ProductAuto.class, autoId1));
            }

            // Delete AUTO record
            boolean autoDeleted = dao.deleteById(ProductAuto.class, autoId2);
            System.out.println("AUTO deleted id=" + autoId2 + "? " + autoDeleted);


            // ========= IDENTITY =========
            ProductIdentity i1 = new ProductIdentity("Keyboard", "IDENTITY - Mechanical", 2500, 30);
            ProductIdentity i2 = new ProductIdentity("Monitor", "IDENTITY - 24 inch", 12000, 12);

            int identityId1 = dao.save(i1);
            int identityId2 = dao.save(i2);

            System.out.println("IDENTITY inserted IDs: " + identityId1 + ", " + identityId2);
            System.out.println("IDENTITY get by id=" + identityId1 + ": " + dao.getById(ProductIdentity.class, identityId1));

            // Update IDENTITY record (only quantity)
            ProductIdentity identityToUpdate = dao.getById(ProductIdentity.class, identityId1);
            if (identityToUpdate != null) {
                identityToUpdate.setQuantity(25);
                dao.update(identityToUpdate);
                System.out.println("IDENTITY after update: " + dao.getById(ProductIdentity.class, identityId1));
            }

            // Delete IDENTITY record
            boolean identityDeleted = dao.deleteById(ProductIdentity.class, identityId2);
            System.out.println("IDENTITY deleted id=" + identityId2 + "? " + identityDeleted);


            // ========= SEQUENCE =========
            // This may fail on MySQL depending on your version/config; keep for "sir requirement"
            ProductSequence s1 = new ProductSequence("SSD", "SEQUENCE - 512GB", 3500, 20);
            ProductSequence s2 = new ProductSequence("RAM", "SEQUENCE - 16GB", 4200, 15);

            int seqId1 = dao.save(s1);
            int seqId2 = dao.save(s2);

            System.out.println("SEQUENCE inserted IDs: " + seqId1 + ", " + seqId2);
            System.out.println("SEQUENCE get by id=" + seqId1 + ": " + dao.getById(ProductSequence.class, seqId1));

            // Update SEQUENCE record (price)
            ProductSequence seqToUpdate = dao.getById(ProductSequence.class, seqId1);
            if (seqToUpdate != null) {
                seqToUpdate.setPrice(3300);
                dao.update(seqToUpdate);
                System.out.println("SEQUENCE after update: " + dao.getById(ProductSequence.class, seqId1));
            }

            // Delete SEQUENCE record
            boolean seqDeleted = dao.deleteById(ProductSequence.class, seqId2);
            System.out.println("SEQUENCE deleted id=" + seqId2 + "? " + seqDeleted);

        } finally {
            HibernateUtil.shutdown();
        }
    }
}