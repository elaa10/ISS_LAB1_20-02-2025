//package ro.iss2025.repository.teste;
//
//import org.junit.jupiter.api.*;
//import ro.iss2025.domain.Book;
//import ro.iss2025.domain.CoverType;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BookRepositoryHibernateLocalTest {
//
//    private BookRepositoryHibernate repo;
//
//    @BeforeEach
//    void setUp() {
//        repo = new BookRepositoryHibernate();
//
//        // curățare înainte de fiecare test (doar dacă vrei să nu se încarce DB-ul)
//        for (Book b : repo.findAll()) {
//            repo.delete(b.getId());
//        }
//    }
//
//    @Test
//    void testSaveBook() {
//        Book book = new Book("Carte Test", "/photo.png", CoverType.HARDCOVER, 300, "Editura X", "Autor Y");
//
//        boolean result = repo.save(book);
//
//        assertTrue(result);
//        assertNotNull(book.getId());
//
//        Book saved = repo.findOne(book.getId());
//        assertEquals("Carte Test", saved.getTitle());
//    }
//
//    @Test
//    void testUpdateBook() {
//        Book book = new Book("Titlu Vechi", "/photo.png", CoverType.PAPERBACK, 123, "Editura", "Autor");
//        repo.save(book);
//
//        book.setTitle("Titlu Nou");
//        repo.update(book);
//
//        Book updated = repo.findOne(book.getId());
//        assertEquals("Titlu Nou", updated.getTitle());
//    }
//
//    @Test
//    void testDeleteBook() {
//        Book book = new Book("De sters", "/photo.png", CoverType.HARDCOVER, 200, "Editura", "Autor");
//        repo.save(book);
//        Integer id = book.getId();
//
//        boolean deleted = repo.delete(id);
//        assertTrue(deleted);
//
//        Book deletedBook = repo.findOne(id);
//        assertNull(deletedBook);
//    }
//
//    @Test
//    void testFindAll() {
//        repo.save(new Book("B1", "/1.png", CoverType.HARDCOVER, 100, "Editura1", "Autor1"));
//        repo.save(new Book("B2", "/2.png", CoverType.PAPERBACK, 200, "Editura2", "Autor2"));
//
//        var all = repo.findAll();
//        assertEquals(2, ((Iterable<?>) all).spliterator().getExactSizeIfKnown());
//    }
//}
