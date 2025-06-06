//package ro.iss2025.repository.test;
//
//import org.junit.jupiter.api.*;
//import ro.iss2025.domain.*;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BorrowRepositoryHibernateTest {
//
//    private static BorrowRepositoryHibernate repo;
//
//    private static User testUser;
//    private static Book testBook;
//    private static Exemplar testExemplar;
//    private static BorrowId testBorrowId;
//
//    @BeforeAll
//    static void setup() {
//        repo = new BorrowRepositoryHibernate();
//
//        // Setăm manual obiectele legate de împrumut
//        testUser = new User("Ana", "ana@email.com", "parola123");
//        testBook = new Book("Cartea Test", "Autorul Test", "Editura Test", 100, "hardcover", "/views/test.png");
//        testExemplar = new Exemplar(testBook, "bună", false);
//
//        try (var session = HibernateUtils.getSessionFactory().openSession()) {
//            var tx = session.beginTransaction();
//            session.persist(testUser);
//            session.persist(testBook);
//            session.persist(testExemplar);
//            tx.commit();
//        }
//
//        LocalDate borrowDate = LocalDate.now();
//        testBorrowId = new BorrowId(testUser, testExemplar, borrowDate);
//    }
//
//    @Test
//    void testSaveAndFindOne() {
//        Borrow borrow = new Borrow(testBorrowId, Borrow.Status.ACTIVE);
//        assertTrue(repo.save(borrow));
//
//        Borrow fromDb = repo.findOne(testBorrowId);
//        assertNotNull(fromDb);
//        assertEquals(Borrow.Status.ACTIVE, fromDb.getStatus());
//    }
//
//    @Test
//    void testUpdate() {
//        Borrow borrow = repo.findOne(testBorrowId);
//        assertNotNull(borrow);
//        borrow.setStatus(Borrow.Status.RETURNED);
//        assertTrue(repo.update(borrow));
//
//        Borrow updated = repo.findOne(testBorrowId);
//        assertEquals(Borrow.Status.RETURNED, updated.getStatus());
//    }
//
//    @Test
//    void testFindAll() {
//        Iterable<Borrow> all = repo.findAll();
//        assertNotNull(all);
//        assertTrue(all.iterator().hasNext());
//    }
//
//    @Test
//    void testDelete() {
//        assertTrue(repo.delete(testBorrowId));
//        assertNull(repo.findOne(testBorrowId));
//    }
//}
