package com.priyakdey.droplet.api.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author Priyak Dey
 */
public abstract class Inode implements Serializable {

    @MongoId
    private ObjectId id;

    private String name;

    private Long ownerId;

    private ObjectId parentId;

    private Instant createdAt;

    private Instant updatedAt;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public ObjectId getParentId() {
        return parentId;
    }

    public void setParentId(ObjectId parentId) {
        this.parentId = parentId;
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
