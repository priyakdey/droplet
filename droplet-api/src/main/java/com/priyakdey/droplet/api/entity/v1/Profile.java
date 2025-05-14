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
@Table(name = "profile", schema = "public")
public class Profile implements Serializable {
    @Serial
    private static final long serialVersionUID = -4575058294451763957L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_profile_id")
    @SequenceGenerator(name = "seq_profile_id", sequenceName = "seq_profile_id", allocationSize = 1)
    private Integer id;

    @Column
    private String name;

    @Column
    private String preferredTz;

    @Column
    private String containerName;

    @Column
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", unique = true)
    private Account account;

    public Profile() {
    }

    public Profile(String name, String preferredTz, String containerName, Account account) {
        this.name = name;
        this.preferredTz = preferredTz;
        this.containerName = containerName;
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
        this.createdAt = now;
        this.updatedAt = now;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreferredTz() {
        return preferredTz;
    }

    public void setPreferredTz(String preferredTz) {
        this.preferredTz = preferredTz;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
