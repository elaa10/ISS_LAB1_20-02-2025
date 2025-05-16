package ro.iss2025.domain;

import jakarta.persistence.*;

@jakarta.persistence.Entity(name = "User")
@Table(name = "users")
public class User extends Entity<Integer> {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String adress;

    @Column(nullable = false, unique = true)
    private String cnp;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean admin;

    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String name, String adress, String cnp, String phoneNumber, Boolean admin, String password) {
        this.name = name;
        this.adress = adress;
        this.cnp = cnp;
        this.phoneNumber = phoneNumber;
        this.admin = admin;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}