package ro.iss2025.domain;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@jakarta.persistence.Entity(name = "Borrow")
@Table(name = "borrows")
public class Borrow extends CompositeEntity<BorrowId> {

    @EmbeddedId
    private BorrowId id;

    @Override
    public BorrowId getId() { return id; }

    @Override
    public void setId(BorrowId id) { this.id = id; }


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exemplarId")
    @JoinColumn(name = "id_exemplar")
    private Exemplar exemplar;

    @Column(name = "date_of_return")
    private ZonedDateTime dateOfReturn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status;

    public Borrow() {
    }

    public Borrow(BorrowId id, User user, Exemplar exemplar, StatusType status) {
        this.setId(id);
        this.user = user;
        this.exemplar = exemplar;
        this.status = status;

        if (id != null && id.getDateOfBorrow() != null) {
            this.dateOfReturn = id.getDateOfBorrow().plusWeeks(2);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public ZonedDateTime getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(ZonedDateTime dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
