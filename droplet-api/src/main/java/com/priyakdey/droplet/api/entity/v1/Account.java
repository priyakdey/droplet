package com.priyakdey.droplet.api.entity.v1;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Clock;
import java.time.ZonedDateTime;

/**
 * @author Priyak Dey
 */
@Entity
@Table(name = "account", schema = "public")
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = -2742305946067119918L;

    private static final int MAX_STORAGE = 104_857_600; // 100MB

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_id")
    @SequenceGenerator(name = "seq_account_id", sequenceName = "seq_account_id", allocationSize = 1)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private Boolean isEnabled;

    @Column(nullable = false)
    private Integer usedStorageInBytes;

    @Column(nullable = false)
    private Integer maxStorageInBytes;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    public Account() {
    }

    public Account(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.isEnabled = true;
        this.usedStorageInBytes = 0;
        this.maxStorageInBytes = MAX_STORAGE;
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Integer getUsedStorageInBytes() {
        return usedStorageInBytes;
    }

    public void setUsedStorageInBytes(Integer usedStorageInBytes) {
        this.usedStorageInBytes = usedStorageInBytes;
    }

    public Integer getMaxStorageInBytes() {
        return maxStorageInBytes;
    }

    public void setMaxStorageInBytes(Integer maxStorageInBytes) {
        this.maxStorageInBytes = maxStorageInBytes;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
