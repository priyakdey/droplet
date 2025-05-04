package com.priyakdey.droplet.api.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Clock;
import java.time.ZonedDateTime;

/**
 * @author Priyak Dey
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 2657921851608477210L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_id_gen")
    @SequenceGenerator(name = "seq_account_id_gen", sequenceName = "seq_account_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column
    private String passwordHash;

    @Column
    private ZonedDateTime createdAt;

    public Account() {
    }

    public Account(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = ZonedDateTime.now(Clock.systemUTC());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
