package ro.iss2025.domain;

import jakarta.persistence.*;

@jakarta.persistence.Entity(name = "Exemplar")
@Table(name = "exemplars")
public class Exemplar extends Entity<Integer> {

    @ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private String condition;

    public Exemplar() {
    }

    public Exemplar(Book book, String condition) {
        this.book = book;
        this.condition = condition;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
