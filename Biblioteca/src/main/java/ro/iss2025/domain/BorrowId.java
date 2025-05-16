package ro.iss2025.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Embeddable
public class BorrowId implements Serializable {

    @Column(name = "id_user")
    private Integer userId;

    @Column(name = "id_exemplar")
    private Integer exemplarId;

    @Column(name = "date_of_borrow")
    private ZonedDateTime dateOfBorrow;

    public BorrowId() {
    }

    public BorrowId(Integer userId, Integer exemplarId, ZonedDateTime dateOfBorrow) {
        this.userId = userId;
        this.exemplarId = exemplarId;
        this.dateOfBorrow = dateOfBorrow;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(Integer exemplarId) {
        this.exemplarId = exemplarId;
    }

    public ZonedDateTime getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(ZonedDateTime dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    // equals() si hashCode() sunt obligatorii pentru chei compuse
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BorrowId)) return false;
        BorrowId that = (BorrowId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(exemplarId, that.exemplarId) &&
                Objects.equals(dateOfBorrow, that.dateOfBorrow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, exemplarId, dateOfBorrow);
    }
}
