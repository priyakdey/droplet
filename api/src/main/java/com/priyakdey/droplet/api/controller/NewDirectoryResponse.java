package com.priyakdey.droplet.api.controller;

import org.bson.types.ObjectId;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Priyak Dey
 */
public class NewDirectoryResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1853150322832928249L;

    private String id;
    private String name;
    private String parentId;
    private Long ownerId;
    private Instant createdAt;
    private Instant updatedAt;

    public NewDirectoryResponse() {
    }

    public NewDirectoryResponse(String id, String name, String parentId, Long ownerId,
                                Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
