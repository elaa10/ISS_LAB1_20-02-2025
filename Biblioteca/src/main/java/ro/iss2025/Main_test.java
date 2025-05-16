package ro.iss2025;

import ro.iss2025.domain.*;
import ro.iss2025.repository.hibernate.*;
import java.time.ZonedDateTime;

public class Main_test {
    public static void main(String[] args) {
        System.out.println("=== Hibernate test ===");

        var bookRepo = new BookRepositoryHibernate();
        var userRepo = new UserRepositoryHibernate();
        var exemplarRepo = new ExemplarRepositoryHibernate();
        var borrowRepo = new BorrowRepositoryHibernate();

        // 1. Salvăm o carte
        Book book = new Book("Test Book", "cover.jpg", CoverType.HARDBACK, 222, "Test Publisher", "John Doe");
        bookRepo.save(book);
        System.out.println("Book saved: " + book.getId());

        // 2. Salvăm un user
        User user = new User("Ana Pop", "Str. Mare 12", "1234567890123", "0777777777", false, "pass123");
        userRepo.save(user);
        System.out.println("User saved: " + user.getId());

        // 3. Salvăm un exemplar pentru cartea de mai sus
        Exemplar exemplar = new Exemplar(book, "Good", false);
        exemplarRepo.save(exemplar);
        System.out.println("Exemplar saved: " + exemplar.getId());

        // 4. Salvăm un împrumut
        ZonedDateTime now = ZonedDateTime.now();
        BorrowId borrowId = new BorrowId(user.getId(), exemplar.getId(), now);
        Borrow borrow = new Borrow(borrowId, user, exemplar, StatusType.BORROWED);
        borrowRepo.save(borrow);
        System.out.println("Borrow saved: " + borrow.getId());

        // 5. Verificăm toate cărțile
        System.out.println("\n=== Books in DB ===");
        bookRepo.findAll().forEach(b -> System.out.println(b.getId() + ": " + b.getTitle()));
    }
}
