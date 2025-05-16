package ro.iss2025.repository.hibernate;

import org.hibernate.Session;
import ro.iss2025.domain.User;
import ro.iss2025.repository.UserRepository;

public class UserRepositoryHibernate implements UserRepository {

    @Override
    public User findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        }
    }

    @Override
    public Iterable<User> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }

    @Override
    public boolean save(User user) {
        return HibernateUtils.inTransaction(session -> {
            session.persist(user);
            return true;
        });
    }

    @Override
    public boolean delete(Integer id) {
        return HibernateUtils.inTransaction(session -> {
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean update(User user) {
        return HibernateUtils.inTransaction(session -> {
            session.merge(user);
            return true;
        });
    }
}
