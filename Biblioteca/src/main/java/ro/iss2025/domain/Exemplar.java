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

    @Column(nullable = false)
    private Boolean booked;

    public Exemplar() {
    }

    public Exemplar(Book book, String condition, Boolean booked) {
        this.book = book;
        this.condition = condition;
        this.booked = booked;
    }

    public Boolean getBooked() {return booked;}

    public void setBooked(Boolean booked) {this.booked = booked;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exemplar)) return false;
        Exemplar that = (Exemplar) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
