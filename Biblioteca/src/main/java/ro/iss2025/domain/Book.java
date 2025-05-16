package ro.iss2025.domain;

import jakarta.persistence.*;

@jakarta.persistence.Entity(name = "Book")
@Table(name = "books")
public class Book extends Entity<Integer> {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(name = "publishing_house", nullable = false)
    private String publishingHouse;

    @Column(name = "nr_pages", nullable = false)
    private Integer nrPages;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover_type", nullable = false)
    private CoverType coverType;

    @Column(nullable = true)
    private String photo;

    public Book(String title, String photo, CoverType coverType, Integer nrPages, String publishingHouse, String author) {
        this.title = title;
        this.photo = photo;
        this.coverType = coverType;
        this.nrPages = nrPages;
        this.publishingHouse = publishingHouse;
        this.author = author;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Integer getNrPages() {
        return nrPages;
    }

    public void setNrPages(Integer nrPages) {
        this.nrPages = nrPages;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

