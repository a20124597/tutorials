package com.baeldung.libraries.jdo;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;
import org.junit.Test;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.baeldung.libraries.jdo.xml.Person;

public class GuideToJDOIntegrationTest {
    @Test
    public void givenProduct_WhenNewThenPerformTransaction() {
        PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumd.addClassName("com.baeldung.libraries.jdo.Product");
        pumd.setExcludeUnlistedClasses();
        pumd.addProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
        pumd.addProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://gzns-matrix-m12-x13-mixed-tuangounuomi-027.gzns.baidu.com:8603/test?createDatabaseIfNotExist=true");
        pumd.addProperty("javax.jdo.option.ConnectionUserName", "root");
        pumd.addProperty("javax.jdo.option.ConnectionPassword", "root");
        pumd.addProperty("datanucleus.autoCreateSchema", "true");
        pumd.addProperty("datanucleus.schema.autoCreateTables", "true");

        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            for (int i = 0; i < 100; i++) {
                String nam = "Product-" + i;
                Product productx = new Product(nam, (double) i);
                pm.makePersistent(productx);
            }
            tx.commit();
        } catch (Throwable thr) {
            fail("Failed test : " + thr.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();
    }

    @Test
    public void givenPerson_WhenNewThenPerformTransaction() {
        Properties props = new Properties();
        props.setProperty ("javax.jdo.option.ConnectionUserName", "root");
        props.setProperty("javax.jdo.option.ConnectionPassword", "root");
        props.setProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://gzns-matrix-m12-x13-mixed-tuangounuomi-027.gzns.baidu.com:8603/test?createDatabaseIfNotExist=true");
        props.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
        props.setProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
        props.setProperty("datanucleus.autoCreateSchema", "true");
        props.setProperty("datanucleus.schema.autoCreateTables", "true");
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory (props);
//        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("META-INF\\datanucleus.properties");
//        PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
//        pumd.addClassName("com.baeldung.libraries.jdo.xml.Person");
//        pumd.setExcludeUnlistedClasses();
//        pumd.addProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
//        pumd.addProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://gzns-matrix-m12-x13-mixed-tuangounuomi-027.gzns.baidu.com:8603/test?createDatabaseIfNotExist=true");
//        pumd.addProperty("javax.jdo.option.ConnectionUserName", "root");
//        pumd.addProperty("javax.jdo.option.ConnectionPassword", "root");
//        pumd.addProperty("datanucleus.autoCreateSchema", "true");
//        pumd.addProperty("datanucleus.schema.autoCreateTables", "true");

//        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            for (int i = 0; i < 100; i++) {
                String nam = "Person-" + i;
                Person personx = new Person(1L, nam, nam);
                personx.setPhoneNumbers("123456789");
                pm.makePersistent(personx);
            }
            tx.commit();
        } catch (Throwable thr) {
            fail("Failed test : " + thr.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();
    }

    @Test
    public void givenProduct_WhenQueryThenExist() {
        PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumd.addClassName("com.baeldung.libraries.jdo.Product");
        pumd.setExcludeUnlistedClasses();
        pumd.addProperty("javax.jdo.option.ConnectionDriverName", "org.h2.Driver");
        pumd.addProperty("javax.jdo.option.ConnectionURL", "jdbc:h2:mem:mypersistence");
        pumd.addProperty("javax.jdo.option.ConnectionUserName", "sa");
        pumd.addProperty("javax.jdo.option.ConnectionPassword", "");
        pumd.addProperty("datanucleus.autoCreateSchema", "true");
        pumd.addProperty("datanucleus.schema.autoCreateTables", "true");

        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Product product = new Product("Tablet", 80.0);
            pm.makePersistent(product);
            Product product2 = new Product("Phone", 20.0);
            pm.makePersistent(product2);
            Product product3 = new Product("Laptop", 200.0);
            pm.makePersistent(product3);
            tx.commit();
        } catch (Throwable thr) {
            fail("Failed test : " + thr.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();

        PersistenceManagerFactory pmf2 = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm2 = pmf2.getPersistenceManager();
        Transaction tx2 = pm2.currentTransaction();
        try {
            tx2.begin();

            @SuppressWarnings("rawtypes")
            Query q = pm2.newQuery("SELECT FROM " + Product.class.getName() + " WHERE price == 200");
            @SuppressWarnings("unchecked")
            List<Product> products = (List<Product>) q.execute();
            for (Product p : products) {
                assertEquals("Laptop", p.name);
            }

            tx2.commit();
        } finally {
            if (tx2.isActive()) {
                tx2.rollback();
            }

            pm2.close();
        }
    }

}