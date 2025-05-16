package ro.iss2025.services;

import ro.iss2025.domain.*;
import ro.iss2025.repository.*;
import ro.iss2025.utils.events.AvailabilityEvent;
import ro.iss2025.utils.events.Event;
import ro.iss2025.utils.observer.Observable;
import ro.iss2025.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Service implements Observable<Event> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ExemplarRepository exemplarRepository;
    private final BorrowRepository borrowRepository;

    private final List<Observer<Event>> observers = new CopyOnWriteArrayList<>();

    public Service(BookRepository bookRepository, UserRepository userRepository,
                   ExemplarRepository exemplarRepository, BorrowRepository borrowRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.exemplarRepository = exemplarRepository;
        this.borrowRepository = borrowRepository;
    }

    public User loginUser(int id, String password) {
        User user = userRepository.findOne(id);
        if (user != null && !user.getAdmin() && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User findUserById(int id) {
        return userRepository.findOne(id);
    }

    public Integer registerUser(String name, String address, String phone, String cnp, String password) {
        User user = new User(name, address, cnp, phone, false, password);
        boolean success = userRepository.save(user);
        return success ? user.getId() : null;
    }

    public List<Exemplar> getAllExemplars() {
        List<Exemplar> all = new ArrayList<>();
        exemplarRepository.findAll().forEach(all::add);
        return all;
    }

    public boolean updateExemplar(Exemplar exemplar) {
        boolean updated = exemplarRepository.update(exemplar);
        if (updated) {
            notifyObservers(new AvailabilityEvent(exemplar));
        }
        return updated;
    }

    public boolean addBorrow(Borrow borrow) {
        boolean added = borrowRepository.save(borrow);
        if (added) {
            notifyObservers(new AvailabilityEvent(borrow.getExemplar()));
        }
        return added;
    }

    public List<Borrow> getAllBorrows() {
        List<Borrow> all = new ArrayList<>();
        borrowRepository.findAll().forEach(all::add);
        return all;
    }

    public boolean updateBorrow(Borrow borrow) {
        boolean updated = borrowRepository.update(borrow);
        if (updated) {
            notifyObservers(new AvailabilityEvent(borrow.getExemplar()));
        }
        return updated;
    }

    public boolean addBook(Book book) {
        boolean added = bookRepository.save(book);
        if (added) {
            notifyObservers(new AvailabilityEvent(null));
        }
        return added;
    }

    public boolean addExemplar(Exemplar exemplar) {
        boolean added = exemplarRepository.save(exemplar);
        if (added) {
            notifyObservers(new AvailabilityEvent(exemplar));
        }
        return added;
    }

    public boolean deleteExemplar(Integer id) {
        boolean deleted = exemplarRepository.delete(id);
        if (deleted) {
            notifyObservers(new AvailabilityEvent(null));
        }
        return deleted;
    }

    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        userRepository.findAll().forEach(all::add);
        return all;
    }

    public boolean deleteUser(Integer id) {
        boolean deleted = userRepository.delete(id);
        if (deleted) {
            notifyObservers(new AvailabilityEvent(null));
        }
        return deleted;
    }

    public boolean updateBook(Book book) {
        boolean updated = bookRepository.update(book);
        if (updated) {
            notifyObservers(new AvailabilityEvent(null));
        }
        return updated;
    }


    @Override
    public void addObserver(Observer<Event> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<Event> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Event event) {
        observers.forEach(obs -> obs.update(event));
    }
}
