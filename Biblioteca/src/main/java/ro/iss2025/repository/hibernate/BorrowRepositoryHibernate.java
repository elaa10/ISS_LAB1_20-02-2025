package ro.iss2025.repository.hibernate;

import org.hibernate.Session;
import ro.iss2025.domain.Borrow;
import ro.iss2025.domain.BorrowId;
import ro.iss2025.repository.BorrowRepository;

public class BorrowRepositoryHibernate implements BorrowRepository {

    @Override
    public Borrow findOne(BorrowId id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Borrow.class, id);
        }
    }

    @Override
    public Iterable<Borrow> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Borrow", Borrow.class).getResultList();
        }
    }

    @Override
    public boolean save(Borrow borrow) {
        return HibernateUtils.inTransaction(session -> {
            session.merge(borrow);
            return true;
        });
    }

    @Override
    public boolean delete(BorrowId id) {
        return HibernateUtils.inTransaction(session -> {
            Borrow borrow = session.find(Borrow.class, id);
            if (borrow != null) {
                session.remove(borrow);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean update(Borrow borrow) {
        return HibernateUtils.inTransaction(session -> {
            session.merge(borrow);
            return true;
        });
    }
}
