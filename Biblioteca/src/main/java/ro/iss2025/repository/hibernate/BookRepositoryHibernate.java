package ro.iss2025.repository.hibernate;

import org.hibernate.Session;
import ro.iss2025.domain.Book;
import ro.iss2025.repository.BookRepository;

import java.util.List;

public class BookRepositoryHibernate implements BookRepository {

    @Override
    public Book findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(Book.class, id);
        }
    }

    @Override
    public Iterable<Book> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Book", Book.class).getResultList();
        }
    }

    @Override
    public boolean save(Book book) {
        return HibernateUtils.inTransaction(session -> {
            session.persist(book);
            return true;
        });
    }

    @Override
    public boolean delete(Integer id) {
        return HibernateUtils.inTransaction(session -> {
            Book book = session.find(Book.class, id);
            if (book != null) {
                session.remove(book);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean update(Book book) {
        return HibernateUtils.inTransaction(session -> {
            session.merge(book);
            return true;
        });
    }
}
