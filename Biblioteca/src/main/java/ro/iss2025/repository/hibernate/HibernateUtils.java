package ro.iss2025.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ro.iss2025.domain.*;

import java.util.Properties;
import java.util.function.Function;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Properties properties = new Properties();
            try {
                properties.load(HibernateUtils.class.getClassLoader().getResourceAsStream("hibernate.properties"));
            } catch (Exception e) {
                throw new RuntimeException("Cannot load hibernate.properties", e);
            }

            sessionFactory = new Configuration()
                    .addProperties(properties)
                    .addAnnotatedClass(Book.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Exemplar.class)
                    .addAnnotatedClass(Borrow.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static <T> T inTransaction(Function<Session, T> command) {
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
