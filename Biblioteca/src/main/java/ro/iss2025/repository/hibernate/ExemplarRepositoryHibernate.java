package ro.iss2025.repository.hibernate;

import org.hibernate.Session;
import ro.iss2025.domain.Exemplar;
import ro.iss2025.repository.ExemplarRepository;

public class ExemplarRepositoryHibernate implements ExemplarRepository {

    @Override
    public Exemplar findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Exemplar.class, id);
        }
    }

    @Override
    public Iterable<Exemplar> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Exemplar", Exemplar.class).getResultList();
        }
    }

    @Override
    public boolean save(Exemplar exemplar) {
        return HibernateUtils.inTransaction(session -> {
            session.persist(exemplar);
            return true;
        });
    }

    @Override
    public boolean delete(Integer id) {
        return HibernateUtils.inTransaction(session -> {
            Exemplar exemplar = session.find(Exemplar.class, id);
            if (exemplar != null) {
                session.remove(exemplar);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean update(Exemplar exemplar) {
        return HibernateUtils.inTransaction(session -> {
            session.merge(exemplar);
            return true;
        });
    }
}
